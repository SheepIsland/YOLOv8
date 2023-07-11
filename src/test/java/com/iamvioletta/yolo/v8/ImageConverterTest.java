package com.iamvioletta.yolo.v8;

import com.iamvioletta.yolo.v8.utils.ImageEncoder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import static org.junit.jupiter.api.Assertions.*;

class ImageConverterTest {
    @Test
    void testBase64Encode() throws IOException {
        final Path imagePath = Paths.get("src/main/resources/test/flowers.jpg");
        final byte[] fileContent = FileUtils.readFileToByteArray(new File(imagePath.toString()));
        final String encodedString = Base64.getEncoder().encodeToString(fileContent);
        final String encodedResult = ImageEncoder.base64Encode(imagePath);

        assertEquals(encodedString, encodedResult);
    }
}
