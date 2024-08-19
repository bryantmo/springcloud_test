package com.bryant.controller;

import com.bryant.service.ChatService;
import com.bryant.service.ImageService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {

    private final ChatService chatService;
    private final ImageService imageService;

    public AiController(ChatService chatService, ImageService imageService) {
        this.chatService = chatService;
        this.imageService = imageService;
    }

    @GetMapping("generate-image")
    public void generateImage(HttpServletResponse response, @RequestParam("prompt") String prompt) throws IOException {
        ImageResponse imageResponse = imageService.generateImage(prompt);

        // Get URL of the generated image
        String imageUrl = imageResponse.getResult().getOutput().getUrl();

        // Send redirect to the image URL
        response.sendRedirect(imageUrl);
    }

    @GetMapping("ask-ai")
    public String askAi(@RequestParam("prompt") String prompt){
        return chatService.queryAi(prompt);
    }


    @GetMapping("city-guide")
    public String cityGuide(@RequestParam("city") String city, @RequestParam("interest") String interest) {
        return chatService.getCityGuide(city, interest);
    }
}