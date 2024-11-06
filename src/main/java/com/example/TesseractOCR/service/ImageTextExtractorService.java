package com.example.TesseractOCR.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageTextExtractorService {

    private final Tesseract tesseract;

    @Value("${tesseract.data.path}")
    String tesseactDataPath;

    public ImageTextExtractorService() {
        tesseract = new Tesseract();
    }

    public String imageExtractor(MultipartFile file) {
        tesseract.setDatapath(tesseactDataPath);  // Path to Tesseract data directory
        // Get the file's original extension (e.g., .jpg, .jpeg, .png)
        String originalFileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        try {
            // Create a temp file with the correct extension
            File tempFile = File.createTempFile("upload-", originalFileExtension);
            file.transferTo(tempFile); // Transfer the contents of MultipartFile to the temp File

            // Perform OCR on the image file
            String text = tesseract.doOCR(tempFile);
            tempFile.delete(); // Clean up the temp file
            return text;
        } catch (TesseractException e) {
            e.printStackTrace();
            return "Error during OCR processing: " + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error handling file: " + e.getMessage();
        }
    }

}
