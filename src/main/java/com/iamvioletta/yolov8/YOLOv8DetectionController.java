package com.iamvioletta.yolov8;

import ai.djl.ModelException;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.translate.TranslateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

import java.util.Map;

@RestController
@RequestMapping("/api/detection")
public class YOLOv8DetectionController {

    @Autowired
    private YOLOv8Service yoloService;

    @PostMapping("/detect")
    public ResponseEntity<Map<String, Object>> detectObjects(@RequestParam("image") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("error", "Image file is required");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            // Save the uploaded file to a temporary location
            Path tempFile = Files.createTempFile("upload", file.getOriginalFilename());
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            // Load and preprocess the image
            Image image = ImageFactory.getInstance().fromFile(tempFile);

            // Perform object detection
            DetectedObjects predictions = yoloService.detectObjects(image);

            // Return the predictions in the response
            // response.put("best", predictions.best().toString());
            response.put("detected", predictions.toJson());
            return ResponseEntity.ok(response);
        } catch (IOException | TranslateException e) {
            response.put("error", "Failed to process the image: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (ModelException e) {
            throw new RuntimeException(e);
        }
    }
}

