package slack;

import webClient.WebClient;

import java.util.HashMap;
import java.util.Map;

public class Slack extends WebClient implements ISlack{

    @Override
    public void sendMessage(String title, String text, String imageUrl) {
        String url = System.getenv("SLACK_WEBHOOK_URL");
        HttpMethod method = HttpMethod.POST;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String payload = """
                {
                  "attachments": [
                    {
                      "title": "%s",
                      "text": "%s",
                      "image_url": "%s"
                    }
                  ]
                }
                """.formatted(title, text, imageUrl);

        try {
            String response = send(makeRequest(url, method, headers, payload));
            logger.info(response);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
