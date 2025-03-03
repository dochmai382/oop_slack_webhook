package llm;

import webClient.WebClient;

import java.util.HashMap;
import java.util.Map;

public class LLM extends WebClient implements ILLM {
    @Override
    public String call_LLM(LLM_Model model, String body) {
        String path = "";
        HttpMethod method = null;
        Map<String, String> headers = new HashMap<>();
        String updatedBody = body;
        switch (model.provider) {
            case GOOGLE -> {
                path = "https://generativelanguage.googleapis.com/v1beta/models/%s:%s?key=%s".formatted(model.name, model.action.name, model.provider.key);
                method = HttpMethod.POST;
                headers.put("Content-Type", "application/json");
            }
            case GROQ -> {
                path = "https://api.groq.com/openai/v1/chat/completions";
                method = HttpMethod.POST;
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + model.provider.key);
                updatedBody = body.replace("modelName", model.name);
            }
            case TOGETHER_AI -> {
                path = "https://api.together.xyz/v1/images/generations";
                method = HttpMethod.POST;
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + model.provider.key);
                updatedBody = body.replace("modelName", model.name);
            }
        }
//        logger.info(path);
        logger.info(updatedBody);
        String result = "";
        try {
            result = send(makeRequest(path, method, headers, updatedBody));
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new RuntimeException(e);
        }

        return result;
    }
}

