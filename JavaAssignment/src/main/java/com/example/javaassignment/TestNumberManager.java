package com.example.javaassignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TestNumberManager {

    private static final String TEST_NUMBER_FILE = "testNumber.txt";

    public static int getNextTestNumber() {
        int testNumber = 1;
        File file = new File(TEST_NUMBER_FILE);
        if (file.exists()) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(TEST_NUMBER_FILE));
                if (!lines.isEmpty()) {
                    testNumber = Integer.parseInt(lines.get(0)) + 1;
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        saveTestNumber(testNumber);
        return testNumber;
    }

    private static void saveTestNumber(int testNumber) {
        try (FileWriter writer = new FileWriter(TEST_NUMBER_FILE)) {
            writer.write(Integer.toString(testNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
