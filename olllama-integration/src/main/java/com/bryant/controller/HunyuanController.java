package com.bryant.controller;

import com.bryant.model.TtsVoice;
import com.bryant.service.HunyuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hunyuan")
@RestController
public class HunyuanController {

    @Autowired
    private HunyuanService hunyuanService;

    @PostMapping("/happy_birthday")
    public String happyBirthday(
            @RequestParam(value = "babyName") String babyName,
            @RequestParam(value = "age")  Integer age,
            @RequestParam(value = "type")  String type,
            @RequestParam(value = "extendInfo")  String extendInfo) {
        return hunyuanService.getContent(babyName, age, type, extendInfo);
    }

    @PostMapping("/get_content_voice")
    public String getContentVoice(
            @RequestParam("text") String text) {
        return hunyuanService.getTTs(text);
    }

    @PostMapping("/create_content_and_voice")
    public String createContentAndVoice(
            @RequestParam(value = "babyName") String babyName,
            @RequestParam(value = "age")  Integer age,
            @RequestParam(value = "type")  String type,
            @RequestParam(value = "extendInfo")  String extendInfo
    ) {
        return hunyuanService.createContentAndVoice(babyName, age, type, extendInfo);
    }

    @GetMapping("/get_voice_by_id")
    public TtsVoice getVoiceById(@RequestParam("id") Long id) {
        return hunyuanService.getVoiceById(id);
    }

}
