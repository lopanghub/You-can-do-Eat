package com.springbootstudy.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.utils.Md5Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }


    public String uploadImage(MultipartFile file) throws IOException {
        File convFile = convertMultiPartToFile(file);
        String fileName = generateFileName(file);
        uploadFileTos3bucket(fileName, convFile);
        return fileName;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3Client.putObject(PutObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .build(), 
            file.toPath());
    }
    
    

    public String downloadFile(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        Path filePath = Paths.get(System.getProperty("java.io.tmpdir"), key); // 임시 디렉토리 사용
        try {
            // 기존 파일이 있으면 덮어쓰기
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            s3Client.getObject(getObjectRequest, filePath);
            System.out.println("Downloaded file path: " + filePath);
            return new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
