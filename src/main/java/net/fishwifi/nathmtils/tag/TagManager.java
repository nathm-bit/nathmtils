package net.fishwifi.nathmtils.tag;

import net.fishwifi.nathmtils.config.ModConfig;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import net.fishwifi.nathmtils.nathmUtils;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TagManager {
    private static final Logger LOGGER = nathmUtils.LOGGER;

    private static final Map<String, String> tags = new HashMap<>();
    private static final Map<String, List<UUID>> players = new HashMap<>();

    private static final ModConfig config = nathmUtils.getConfig();
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void loadTags() {
        tags.clear();
        players.clear();

        executor.submit(() -> {
            HttpURLConnection connection = null;

            try {
                URL url = new URL(config.getPlayerTagDataURL());
                LOGGER.info("Attempting to fetch tags from {}", url);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != 200) {
                    throw new RuntimeException("Failed to fetch tags, HTTP error: " + responseCode);
                }

                try (InputStream inputStream = connection.getInputStream();
                     InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                     BufferedReader bufferedReader = new BufferedReader(reader)) {
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    String response = responseBuilder.toString();

                    try {
                        Gson gson = new Gson();
                        Type dataType = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
                        Map<String, Map<String, Object>> data = gson.fromJson(response, dataType);

                        if (data != null) {
                            Map<String, Object> tagData = data.get("tags");
                            Map<String, Object> playerData = data.get("players");

                            if (tagData != null) {
                                for (Map.Entry<String, Object> entry : tagData.entrySet()) {
                                    if (entry.getValue() instanceof String) {
                                        tags.put(entry.getKey(), (String) entry.getValue());
                                                }
                                }
                            }

                            if (playerData != null) {
                                for (Map.Entry<String, Object> entry : playerData.entrySet()) {
                                    if (entry.getValue() instanceof List<?> uuidList) {
                                        List<UUID> uuids = uuidList.stream()
                                                .filter(obj -> obj instanceof String)
                                                .map(obj -> UUID.fromString((String) obj))
                                                .toList();
                                        players.put(entry.getKey(), uuids);
                                    }
                                }
                            }

                            LOGGER.info("Loaded player tags: {}", tags);
                            LOGGER.info("Loaded player data: {}", players);
                        }
                    } catch (JsonSyntaxException e) {
                        LOGGER.error("The server returned invalid json! {}", response);
                        throw e;
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Failed to fetch player tags", e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }

    public static String getTagForPlayer(UUID playerUUID) {
        if (!config.PlayerTagsEnabled()) {
            return null;
        }

        for (Map.Entry<String, List<UUID>> entry : players.entrySet()) {
            if (entry.getValue().contains(playerUUID)) {
                return tags.get(entry.getKey());
            }
        }
        return null;
    }
}
