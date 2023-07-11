package com.iamvioletta.yolo.v8.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;

public class ImageConverter {
    public static String base64Encode(Path filePath) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath.toString()));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }
    public static void base64Decode(byte[] encodedString, Path outputPath) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        FileUtils.writeByteArrayToFile(new File(outputPath.toString()), decodedBytes);
    }
}
