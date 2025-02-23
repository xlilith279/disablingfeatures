package com.xlilith.disablingFeatures;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordHandler {
    private final String webhookURL;
    
    public DiscordHandler(String webhookURL) {
        this.webhookURL = webhookURL;
    }
    
    public void sendToDiscord(String message) {
        try {
            URI uri = URI.create(webhookURL);
            URL url = uri.toURL();
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            String jsonPayload = "{\"content\": \"" + message + "\"}";
            
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            connection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
