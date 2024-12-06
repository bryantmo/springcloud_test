package com.bryant.controller;

import com.bryant.model.TtsVoice;
import com.bryant.service.HunyuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
    public Long createContentAndVoice(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "age")  Integer age,
            @RequestParam(value = "type")  String type,
            @RequestParam(value = "extendInfo")  String extendInfo
    ) {
        TtsVoice ttsvo = hunyuanService.createContentAndVoice(name, age, type, extendInfo);
        return ttsvo.getId();
    }

    @GetMapping("/get_voice_by_id")
    public String getVoiceById(@RequestParam("id") String id) {
        TtsVoice voice = hunyuanService.getVoiceById(Long.valueOf(id));
        if (Objects.isNull(voice)) {
            return "null";
        }
        return voice.getVoice();
    }

}
