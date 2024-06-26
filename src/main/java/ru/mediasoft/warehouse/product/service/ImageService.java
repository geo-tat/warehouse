package ru.mediasoft.warehouse.product.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public interface ImageService {
    UUID upload(UUID id, MultipartFile file) throws IOException;

    void download(UUID id, OutputStream outputStream) throws IOException;
}
