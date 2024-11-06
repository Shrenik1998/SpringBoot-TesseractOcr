package com.example.TesseractOCR.controller;


import com.example.TesseractOCR.service.ImageTextExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ocr")
public class ImageTextExtractorController {

    @Autowired
    private ImageTextExtractorService imageTextExtractorService;

    @PostMapping("/getText")
    public ResponseEntity<String> ocr(@RequestParam("file") MultipartFile file){
        String extractedText = imageTextExtractorService.imageExtractor(file);
        return new ResponseEntity<>(extractedText, HttpStatus.OK);
    }
}
