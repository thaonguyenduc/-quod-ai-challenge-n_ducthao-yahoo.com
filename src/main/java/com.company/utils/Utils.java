package com.company.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Utils {

    public static File createTempFile(String fileName, String extension) throws IOException {
        return Files.createTempFile(fileName, extension).toFile();
    }
}
