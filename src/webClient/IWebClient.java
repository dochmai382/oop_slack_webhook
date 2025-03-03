package webClient;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface IWebClient {

    Logger logger = Logger.getLogger(IWebClient.class.getName());
    default void setLoggerLevel(Level level) {
        logger.setLevel(level);
    }

    enum HttpMethod {
        GET("GET"),
        POST("POST");

        final String type;
        HttpMethod(String type) {
            this.type = type;
        }
    }

    String send(HttpRequest request) throws IOException, InterruptedException;
    HttpRequest makeRequest(String url, HttpMethod type, Map<String, String> headers, String body);
}
