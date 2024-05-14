package net.fishwifi.nathmtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.Map;

public class ModConfig {
    private static final String CONFIG_DIR = "config/nathm-utils";
    private static final String CONFIG_FILE = "config.json";
    public static String customText;

    public static void load() {
        try {
            File configFile = new File(Paths.get(System.getProperty("user.dir"), CONFIG_DIR, CONFIG_FILE).toString());

            // Check if the config file exists
            if (!configFile.exists()) {
                nathmUtils.LOGGER.warn("Config file does not exist: " + configFile.getPath() + ", using default text");
                customText = "nathm's utils"; // Fallback text if no config file
                return;
            }

            // Prepare to read the JSON configuration
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, String>>() {}.getType(); // Define the type of Map

            try (FileReader reader = new FileReader(configFile)) {
                Map<String, String> config = gson.fromJson(reader, mapType); // Deserialize JSON to Map
                customText = config.getOrDefault("versionText", "nathm's utils");
            }
        } catch (Exception e) {
            nathmUtils.LOGGER.warn("Failed to load config: " + e.getMessage());
            customText = "nathm's utils"; // Fallback text in case of errors
        }
    }
}
