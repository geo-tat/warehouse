package ru.mediasoft.warehouse.product.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ImageService {
    String upload(UUID id, MultipartFile file) throws IOException;

    byte[] download(UUID id) throws IOException;
}
