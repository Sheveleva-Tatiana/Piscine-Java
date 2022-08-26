package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Parser {
    public static HashMap<String, String> parseFile(String filePath, String profile) {
        HashMap<String, String> properties = new HashMap<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                String[] keyValuePair = line.split("=");
                if (keyValuePair.length == 2) {
                    String key = keyValuePair[0].trim();
                    String value = keyValuePair[1].trim();
                    if (value.isEmpty()) {
                        value = " ";
                    }
                    properties.put(key, value);
                } else if (keyValuePair.length == 1) {
                    String key = keyValuePair[0].trim();
                    String value = " ";
                    properties.put(key, value);
                } else {
                    System.err.println("Invalid configuration file");
                    System.exit(-1);
                }
            }
        } catch (Exception e) {
            System.err.println("Properties " + profile + " wasn't find.");
            System.exit(-1);
        }
        return properties;
    }
}
