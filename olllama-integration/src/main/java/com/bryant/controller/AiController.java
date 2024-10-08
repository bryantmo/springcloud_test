package com.bryant.controller;

import com.bryant.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {

    private final ChatService chatService;

    public AiController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/city-guide")
    public String askAi(
            @RequestParam("city") String city,
            @RequestParam("interest") String interest) {
        return chatService.getCityGuide(city, interest);
    }

    @GetMapping("/ask_ai")
    public String cityGuide(@RequestParam("question") String question) {
        return chatService.askAi(question);
    }

    @PostMapping("/embedding")
    public String embedding(
            @RequestParam("prompt") String prompt,
            @RequestParam("temperature") Integer temperature) {
        return chatService.embedding(prompt, temperature);
    }

    @GetMapping("/model_show")
    public String modelShow() {
        return chatService.modelShow();
    }

}