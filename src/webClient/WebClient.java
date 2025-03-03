package webClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class WebClient implements IWebClient{
    protected HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    public String send(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(response.statusCode() + "");
        logger.info(response.body());
        return response.body();
    }

    @Override
    public HttpRequest makeRequest(String url, HttpMethod type, Map<String, String> headers, String body) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method(type.type, HttpRequest.BodyPublishers.ofString(body));
        headers.forEach(builder :: header);

        return builder.build();
    }
}
