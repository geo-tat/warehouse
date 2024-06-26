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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    public UUID upload(UUID id, MultipartFile file) throws IOException {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар на складе не найден."));
        final File convertedFiles = convertMultiPartFileToFile(file);
        final UUID key = storage.uploadFile(convertedFiles);
        final Image image = Image.builder()
                .product(product)
                .fileKey(key)
                .build();
        repository.save(image);
        return key;
    }

    public void download(UUID id, OutputStream outputStream) throws IOException {
        final List<Image> images = repository.findAllByProductId(id);
        final List<UUID> fileKeys = images.stream().map(Image::getFileKey).toList();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            for (UUID fileKey : fileKeys) {
                byte[] fileContent = storage.downloadFile(fileKey);
                ZipEntry zipEntry = new ZipEntry(String.valueOf(fileKey));
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(fileContent);
                zipOutputStream.closeEntry();
            }
            zipOutputStream.finish();
        }
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        final File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

}
