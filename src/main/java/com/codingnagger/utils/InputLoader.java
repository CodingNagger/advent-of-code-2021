package com.codingnagger.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InputLoader {
  public List<String> Load(String filename) throws IOException {
    return Files.readAllLines(Paths.get("src","main","resources", filename));
  }
}
