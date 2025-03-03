import llm.LLM;
import slack.Slack;

import java.util.logging.Level;

import static llm.ILLM.LLM_Model.FLUX_1_SCHNELL_FREE;
import static llm.ILLM.LLM_Model.GEMINI_2_0_FLASH;
import static webClient.IWebClient.logger;

public class App {
    public static void main(String[] args) {
        LLM llm = new LLM();
        String prompt = "베이스의 매력이 무엇인지 설명해주고 그걸 잘 나타낸 음악 1곡 추천(queen 제외). no markdown, nutshell. 추천곡엔 강조표시";

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
        String imagePrompt = "generate a suitable image based on %s exclude people. non-people".formatted(result);
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
        String title = "베이스의 매력을 모르는 당신!!";
        slack.sendMessage(title, result, imageResult);
    }
}
