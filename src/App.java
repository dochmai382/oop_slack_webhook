import llm.LLM;
import slack.Slack;

import java.util.logging.Level;

import static llm.ILLM.LLM_Model.FLUX_1_SCHNELL_FREE;
import static llm.ILLM.LLM_Model.GEMINI_2_0_FLASH;
import static webClient.IWebClient.logger;

public class App {
    public static void main(String[] args) {
        LLM llm = new LLM();
//        String prompt = "";
        String prompt = System.getenv("LLM_PROMPT");

        logger.setLevel(Level.SEVERE);
        String result = llm.call_LLM(GEMINI_2_0_FLASH, """
                {
                  "contents": [
                    {
                      "role": "user",
                      "parts": [
                        {
                          "text": "%s"
                        }
                      ]
                    }
                  ],
                }
                """.formatted(prompt));
        result = result.split("\"text\": \"")[1].split("}")[0]
                .replace("\\n", " ").replace("\\", "").replace("\"","").trim();
        logger.info(result);

        // 이미지 생성
        String template = System.getenv("LLM_IMAGE_PROMPT");
        String imagePrompt = template.formatted(result);
//        String imageResult = llm.call_LLM(STABLE_DIFFUSION_XL_BASE_1_0, """
        String imageResult = llm.call_LLM(FLUX_1_SCHNELL_FREE, """
                {
                    "model": "modelName",
                    "prompt": "%s",
                    "width": 512,
                    "height": 512,
                    "steps": 4,
                    "n": 1
                   }
                """.formatted(imagePrompt));

        imageResult = imageResult.split("\"url\": \"")[1].split("\"")[0];
        logger.info(imageResult);

        Slack slack = new Slack();
//        String title = "###";
        String title = System.getenv("SLACK_WEBHOOK_TITLE");
        slack.sendMessage(title, result, imageResult);
    }
}
