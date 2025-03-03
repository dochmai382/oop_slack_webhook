import llm.ILLM;
import llm.LLM;

import java.util.logging.Level;

import static webClient.IWebClient.logger;

public class App {
    public static void main(String[] args) {
        LLM llm = new LLM();
        String prompt = "베이스의 매력이 무엇인지 설명해주고 그걸 잘 나타낸 음악 추천. no markdown, nutshell";
//        logger.setLevel(Level.SEVERE);
        String result = llm.call_LLM(ILLM.LLM_Model.GEMINI_2_0_FLASH, """
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
                .replace("\\n", " ").replace("\"", "");
        logger.info(result);
    }
}
