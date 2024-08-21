package com.bryant.service;

import com.bryant.config.OllamaConstants;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("timeOutRestTemplate")
    private RestTemplate timeOutRestTemplate;

    public String getCityGuide(String city, String interest) {
        String prompt = String.format(OllamaConstants.CITY_GUIDE_PROMPT_TEMPLATE, city, interest);
        HashMap<String, Object> map = new HashMap<>();
        map.put("prompt", prompt);
        map.put("model", OllamaConstants.OLLAMA_MODEL_NAME);
        map.put("stream", false);
        ResponseEntity<JsonNode> stringResponseEntity = timeOutRestTemplate.postForEntity(OllamaConstants.API_GENERATE, map, JsonNode.class);
        HttpStatus statusCode = stringResponseEntity.getStatusCode();

        // 大模型请求成功
        if (statusCode != HttpStatus.OK) {
            return "first request to ollama failed";
        }

        JsonNode body = stringResponseEntity.getBody();
        JsonNode firstAnswerResponseText = body.get("response");

        // 第二轮对话
        map.put("prompt", "so can you tell me more about it?");
        map.put("model", OllamaConstants.OLLAMA_MODEL_NAME);
        map.put("context", body.get("context"));
        map.put("stream", false);
        ResponseEntity<JsonNode> stringResponseEntity2 = timeOutRestTemplate.postForEntity(OllamaConstants.API_GENERATE, map, JsonNode.class);
        JsonNode body2 = stringResponseEntity2.getBody();
        JsonNode firstAnswerResponseText2 =  body2.get("response");

        return "firstAnswerResponseText: " + firstAnswerResponseText.asText()
                + "\n"
                + " secondAnswerResponseText: " + firstAnswerResponseText2.asText();
    }

    public String askAi(String question) {
        String prompt = String.format(OllamaConstants.AI_ASK_PROMPT_TEMPLATE, question);
        HashMap<String, Object> map = new HashMap<>();
        map.put("prompt", prompt);
        map.put("model", OllamaConstants.OLLAMA_MODEL_NAME);
        map.put("stream", false);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(OllamaConstants.API_GENERATE, map, String.class);
        return stringResponseEntity.getBody();
    }

    public String embedding(String prompt, Integer temperature) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("prompt", prompt);
        map.put("model", OllamaConstants.OLLAMA_MODEL_NAME);
        HashMap<String, Object> options = new HashMap<>();
        map.put("options", options);
        // temperature	模型的温度。增加温度将使模型回答更具创造性。（默认：0.8）
        options.put("temperature", temperature);
        map.put("stream", false);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(OllamaConstants.API_EMBEDDINGS, map, String.class);
        return responseEntity.getBody();
    }

    public String modelShow() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("model", OllamaConstants.OLLAMA_MODEL_NAME);
        map.put("stream", false);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(OllamaConstants.API_SHOW, map, String.class);
        return responseEntity.getBody();
    }
}