package ru.mediasoft.warehouse.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Getter
public class S3Configuration {
    private final S3Properties properties;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(properties.getPath(), properties.getRegion()))
                .enablePathStyleAccess()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

/*

    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        properties.getPath(),
                        properties.getRegion()))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(properties.getAccessKey(),
                                properties.getSecretKey())))
                .build();
    }


 */
}
