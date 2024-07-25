package com.bryant.controller;

import com.bryant.flux.ChatMsgFluxUnit;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class ChatMsgController {
    @Resource
    private ChatMsgFluxUnit<String, Map<String,String>> chatMsgFluxUnit;
    
    @GetMapping(value = "/flux",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @CrossOrigin(origins = "*")
    public Flux<Map<String,String>> flux(@RequestParam("content") String content){
        return chatMsgFluxUnit.getMoreChatMsg(
                "你要问啥？请详细描述一下你的问题......" + content,
                    s -> {
                        Map<String, String> map = new HashMap<>(1);
                        map.put("msg", s);
                        return map;
                    }
                );
    }
}