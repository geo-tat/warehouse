package ru.mediasoft.warehouse.product.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mediasoft.warehouse.product.model.Image;
import ru.mediasoft.warehouse.product.model.Product;
import ru.mediasoft.warehouse.product.repository.ImageRepository;
import ru.mediasoft.warehouse.product.repository.ProductRepository;
import ru.mediasoft.warehouse.product.storage.S3Storage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ProductRepository productRepository;
    private final ImageRepository repository;
    private final S3Storage storage;

    @Override
    public String upload(UUID id, MultipartFile file) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар на складе не найден."));
        File convetedFiles = convertMultiPartFileToFile(file);
        String key = storage.uploadFile(convetedFiles);
        Image image = Image.builder()
                .product(product)
                .fileKey(key)
                .build();
        Image finalImage = repository.save(image);
        Long imageId = finalImage.getId();
        return key;
    }

    @Override
    public byte[] download(UUID id) throws IOException {

        List<Image> images = repository.findAllByProductId(id);
        List<String> fileKeys = images.stream().map(Image::getFileKey).toList();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

        for (String fileKey : fileKeys) {
            byte[] fileContent = storage.downloadFile(fileKey);
            ZipEntry zipEntry = new ZipEntry(fileKey);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(fileContent);
            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();

        return byteArrayOutputStream.toByteArray();
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
