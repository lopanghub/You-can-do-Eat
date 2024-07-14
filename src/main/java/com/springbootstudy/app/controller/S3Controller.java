package com.springbootstudy.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springbootstudy.app.service.S3Service;

//s3 파일 업로드다운로드 컨트롤러 - 담당자 이현학
@RestController
@RequestMapping("/api/files")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam String key, @RequestBody String content) {
        s3Service.uploadFile(key, content);
        return ResponseEntity.ok("File uploaded successfully: " + key);
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadFile(@RequestParam String key) throws IOException {
        String content = s3Service.downloadFile(key);
        return ResponseEntity.ok(content);
    }
}
