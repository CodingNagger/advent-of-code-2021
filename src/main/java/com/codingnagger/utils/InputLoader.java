package com.codingnagger.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InputLoader {
    public static List<String> Load(String filename) throws IOException {
        return Load("main", filename);
    }

    public static List<String> LoadTest(String filename) throws IOException {
        return Load("test", filename);
    }

    public static List<String> Load(String resourcesParent, String filename) throws IOException {
        return Files.readAllLines(Paths.get("src", resourcesParent, "resources", filename));
    }
}
