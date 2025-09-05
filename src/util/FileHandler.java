package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public static List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            // File might not exist yet, which is okay
            System.out.println("Note: File " + filename + " doesn't exist yet or can't be read.");
        }
        return lines;
    }
    
    public static void writeFile(String filename, List<String> lines) {
        createDirectoryIfNotExists(filename);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    public static void appendToFile(String filename, String line) {
        createDirectoryIfNotExists(filename);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
        }
    }
    
    private static void createDirectoryIfNotExists(String filename) {
        File file = new File(filename);
        File parentDir = file.getParentFile();
        
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }
}