package llm;

public interface ILLM {

    enum LLM_Provider {
        GOOGLE(System.getenv("GOOGLE_API_KEY")),
        GROQ(System.getenv("GROQ_API_KEY")),
        TOGETHER_AI(System.getenv("TOGETHER_AI_API_KEY"));

        final String key;
        LLM_Provider(String key) {
            this.key = key;
        }
    }

    enum LLM_Action {
        GENERATE_CONTENT("generateContent");

        final String name;
        LLM_Action(String name) {
            this.name = name;
        }
    }

    enum LLM_Model {
        GEMINI_2_0_FLASH(LLM_Provider.GOOGLE, "gemini-2.0-flash", LLM_Action.GENERATE_CONTENT),
        MIXTRAL_8_X_7_B_32768(LLM_Provider.GROQ, "mixtral-8x7b-32768", null),
        STABLE_DIFFUSION_XL_BASE_1_0(LLM_Provider.TOGETHER_AI, "stabilityai/stable-diffusion-xl-base-1.0", null),
        FLUX_1_SCHNELL_FREE(LLM_Provider.TOGETHER_AI, "black-forest-labs/FLUX.1-schnell-Free", null);

        final LLM_Provider provider;
        final String name;
        final LLM_Action action;

        LLM_Model(LLM_Provider provider, String name, LLM_Action action) {
            this.provider = provider;
            this.name = name;
            this.action = action;
        }
    }

    String call_LLM(LLM_Model model, String body);
}
