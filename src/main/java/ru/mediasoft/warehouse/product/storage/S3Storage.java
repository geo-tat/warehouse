package ru.mediasoft.warehouse.product.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mediasoft.warehouse.config.S3Properties;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Storage {
    private final AmazonS3 s3Client;
    private final S3Properties properties;

    public UUID uploadFile(File convertedFile) throws IOException {

        final String bucketName = properties.getBucketName();

        if (!s3Client.doesBucketExistV2(bucketName)) {
            s3Client.createBucket(bucketName);
        }
        final UUID fileKey = UUID.randomUUID();
        s3Client.putObject(new PutObjectRequest(bucketName, fileKey.toString(), convertedFile));
        convertedFile.delete();
        return fileKey;
    }


    public byte[] downloadFile(UUID fileKey) throws IOException {
        final String bucketName = properties.getBucketName();
        final S3Object s3Object = s3Client.getObject(bucketName, String.valueOf(fileKey));
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final byte[] read_buf = new byte[1024];
        int read_len;
        while ((read_len = inputStream.read(read_buf)) > 0) {
            outputStream.write(read_buf, 0, read_len);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }
}
