package com.iamvioletta.yolo.v8;

import com.iamvioletta.yolo.v8.controller.DetectionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DetectionControllerTest {
    private DetectionController detectionController;

    @BeforeEach
    void setUp() {
        detectionController = new DetectionController(new TestYoloDetector());
    }

    @Test
    void testDetectObjects_WithValidImage() throws IOException {
        Path imagePath = Paths.get(
                "src/main/resources/test/flowers.jpg");
        byte[] fileContent = Files.readAllBytes(imagePath);
        MultipartFile file = new MockMultipartFile("image.jpg", fileContent);

        ResponseEntity<Map<String, Object>> response = detectionController.detectObjects(file);

        // Assert the response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert the response body
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertTrue(response.getBody().containsKey("detected")),
                () -> assertTrue(response.getBody().containsKey("base64Image")),
                () -> assertNotNull(response.getBody().get("detected")),
                () -> assertNotNull(response.getBody().get("base64Image"))
        );
    }

    @Test
    void testDetectObjects_WithEmptyImage() {
        // Create an empty MultipartFile
        MultipartFile file = new MockMultipartFile("empty.jpg", new byte[0]);

        ResponseEntity<Map<String, Object>> response = detectionController.detectObjects(file);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
