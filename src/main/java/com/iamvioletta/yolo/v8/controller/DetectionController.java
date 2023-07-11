package com.iamvioletta.yolo.v8.controller;

import ai.djl.ModelException;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.translate.TranslateException;
import com.iamvioletta.yolo.v8.dl.YoloDetector;
import com.iamvioletta.yolo.v8.utils.ImageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Controller for applying yolov8 model.
 */
@RestController
@RequestMapping("/api/detection")
public class DetectionController {
    private final Logger logger = LoggerFactory.getLogger(DetectionController.class);

    @Autowired
    private YoloDetector yoloService;

    /**
     *   Detect objects on image, path to image is required.
     */
    @PostMapping("/detect")
    public ResponseEntity<Map<String, Object>> detectObjects(@RequestParam("image") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            logger.error("Image file is empty");
            response.put("error", "Image file is required");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            Path tempFile = Files.createTempFile("upload", file.getOriginalFilename());
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
            logger.debug("Saved the uploaded image to a temporary location: " + tempFile);

            Image image = ImageFactory.getInstance().fromFile(tempFile);
            DetectedObjects predictions = yoloService.detectObjects(image);
            logger.debug("Performed object detection");

            Path detectedImage = Files.createTempFile("detected", file.getOriginalFilename());
            image.save(Files.newOutputStream(detectedImage), "png");
            logger.info("Performed objects detection and saved detected image to a temporary location: "
                    + detectedImage);

            response.put("detected", predictions.toJson());
            response.put("base64Image", ImageConverter.base64Encode(detectedImage));
            logger.info("Created a response entity");

            return ResponseEntity.ok(response);
        } catch (IOException | TranslateException e) {
            response.put("error", "Failed to process the image: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (ModelException e) {
            throw new RuntimeException(e);
        }
    }
}

