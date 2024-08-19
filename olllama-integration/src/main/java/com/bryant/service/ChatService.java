package com.bryant.service;

import java.util.HashMap;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String queryAi(String prompt) {
        return chatClient.call(prompt);
    }

    public String getCityGuide(String city, String interest) {
        String template = "I am a tourist visiting the city of {city}.\n"
                + "                I am mostly interested in {interest}.\n"
                + "                Tell me tips on what to do there.";

        PromptTemplate promptTemplate = new PromptTemplate(template);

        HashMap<String, Object> params = new HashMap<>();
        params.put("city", city);
        params.put("interest", interest);
        Prompt prompt = promptTemplate.create(params);

        return chatClient.call(prompt).getResult().getOutput().getContent();
    }

}