package com.bryant.config;

public class OllamaConstants {

    public static final String AI_ASK_PROMPT_TEMPLATE = "I am a student from the University of Washington."
            + "I want to ask you a question: %s ";
    public static String CITY_GUIDE_PROMPT_TEMPLATE = "I am a tourist visiting the city of %s."
            + "I am mostly interested in %s."
            + "Tell me tips on what to do there.";

    public static final String OLLAMA_URL = "http://127.0.0.1:11434";
    //生成补全 POST
    public static final String API_GENERATE = OLLAMA_URL + "/api/generate";
    //对话补全 POST
    public static final String API_CHAT = OLLAMA_URL + "/api/chat";
    //列出本地模型
    public static final String API_LIST = "/api/tags";
    //显示模型信息
    public static final String API_SHOW = "/api/show";
    //生成嵌入
    public static final String API_EMBEDDINGS = "/api/embeddings";

}
