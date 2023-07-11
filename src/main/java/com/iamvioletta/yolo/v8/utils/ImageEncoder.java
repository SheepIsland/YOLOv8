package com.iamvioletta.yolo.v8.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;

public class ImageEncoder {
    public static String base64Encode(Path filePath) throws IOException {
        final byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath.toString()));
        final String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }
}
