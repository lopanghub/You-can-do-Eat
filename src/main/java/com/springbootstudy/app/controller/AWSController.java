package com.springbootstudy.app.controller;

import com.springbootstudy.app.service.AWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//aws 테스트용 컨트롤러
@RestController
@RequestMapping("/aws")
@RequiredArgsConstructor
public class AWSController {

    private final AWSService awsService;

    @GetMapping("/load-recipes")
    public ResponseEntity<String> loadRecipesFromS3() {
        awsService.init();
        return ResponseEntity.ok("Recipes loaded successfully from S3 and inserted into the database.");
    }
}
