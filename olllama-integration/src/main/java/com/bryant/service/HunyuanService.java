package com.bryant.service;

import com.bryant.mapper.TtsVoiceMapper;
import com.bryant.model.TtsVoice;
import com.bryant.service.util.HunyuanClient;
import com.bryant.service.util.ObjectTypeEnum;
import com.bryant.service.util.TtsClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tencentcloudapi.common.SSEResponseModel;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.hunyuan.v20230901.models.ChatCompletionsRequest;
import com.tencentcloudapi.hunyuan.v20230901.models.ChatCompletionsResponse;
import com.tencentcloudapi.hunyuan.v20230901.models.Choice;
import com.tencentcloudapi.hunyuan.v20230901.models.Message;
import com.tencentcloudapi.tts.v20190823.models.TextToVoiceRequest;
import com.tencentcloudapi.tts.v20190823.models.TextToVoiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class HunyuanService {

    private final static String PROMOTE_TEMPLATE = "今天是 %s %s 的 %s 岁生日，希望你能帮忙写一段200字左右的祝福语，要求是 %s，输出内容限制在120字以内";

    @Autowired
    private HunyuanClient client;

    @Autowired
    private TtsClient ttsClient;

    @Autowired
    private TtsVoiceMapper ttsVoiceMapper;

    public String getContent(String name, Integer age, String type, String extendInfo) {
        ChatCompletionsRequest req = new ChatCompletionsRequest();
        String content = String.format(PROMOTE_TEMPLATE, name, ObjectTypeEnum.fromCode(type).getChName(), age, extendInfo);
        Message[] msgs = new Message[1];
        Message msg = new Message();
        msg.setContent(content);
        msgs[0] = msg;
        req.setMessages(msgs);
        // hunyuan ChatCompletions 同时支持 stream 和非 stream 的情况
        req.setStream(true);
        msg.setRole("user");
        req.setModel("hunyuan-standard");
        try {
            ChatCompletionsResponse resp = client.ChatCompletions(req);
            StringBuilder stringBuilder = new StringBuilder();
            if (req.getStream()) {
                // stream 示例
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                for (SSEResponseModel.SSE e : resp) {
                    ChatCompletionsResponse eventModel = gson.fromJson(e.Data, ChatCompletionsResponse.class);
                    Choice[] choices = eventModel.getChoices();
                    if (choices.length > 0) {
                        log.info("choices[0].getDelta().getContent() = {}", choices[0].getDelta().getContent());
                        stringBuilder.append(choices[0].getDelta().getContent());
                    }

                    // 如果希望在任意时刻中止事件流, 使用 resp.close() + break
                    boolean iWantToCancelNow = false;
                    if (iWantToCancelNow) {
                        resp.close();
                        break;
                    }
                }
                return stringBuilder.toString();
            } else {
                // 非 stream 示例
                // 通过 Stream=false 参数来指定非 stream 协议, 一次性拿到结果
                System.out.println(resp.getChoices()[0].getMessage().getContent());
            }
        } catch (TencentCloudSDKException e) {
            log.info("TencentCloudSDKException, {}", e);
        }
        return "error";
    }

    public String getTTs(String text) {
        TextToVoiceRequest request = new TextToVoiceRequest();
        try {
            request.setText(text);
            request.setSkipSign(true);
            request.setModelType(5l);
            request.setVoiceType(301022l);
            request.setVolume(1f);
            request.setSpeed(1f);
            request.setCodec("wav");
            request.setSessionId(UUID.randomUUID().toString());
            request.setEmotionCategory("happy");
            request.setEmotionIntensity(150L);
            TextToVoiceResponse resp = ttsClient.TextToVoice(request);
            try {
//                TextToVoiceResponse.toJsonString(resp);

                // 存储的是base64解码后的数据
//                byte[] audioBytes = Base64.getDecoder().decode(resp.getAudio());
//                String filePath = "olllama-integration/src/main/resources/voice/tts.wav";
//                FileOutputStream fos = new FileOutputStream(filePath);
//                fos.write(audioBytes);

                // 存储的是base64解码前的数据
                String filePath2 = "olllama-integration/src/main/resources/voice/tts2.wav";
                FileOutputStream fos2 = new FileOutputStream(filePath2);
                fos2.write(resp.getAudio().getBytes());
                fos2.close();

                return resp.getAudio();
            } catch (IOException e) {
                log.info("ex, ", e);
            }

        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }

        return "fail";
    }


    public TtsVoice createContentAndVoice(String babyName, Integer age, String type, String extendInfo) {

        StopWatch watch = new StopWatch("createContentAndVoice");

        watch.start("getContent");
        // 1、ai文生文
        String content = this.getContent(babyName, age, type, extendInfo);
        watch.stop();

        watch.start("getTTs");
        // 2、语音合成
        String tts = this.getTTs(content);
        watch.stop();

        // 3、保存到数据库
        watch.start("insert tts");
        TtsVoice ttsVoice = buildTtsVoice(content, type, age, babyName, tts);
        ttsVoiceMapper.insert(ttsVoice);
        watch.stop();

        log.info("createContentAndVoice, {}", watch);

//        return tts;
        return ttsVoice;
    }

    private TtsVoice buildTtsVoice(String content, String type, Integer age, String babyName, String tts) {
        TtsVoice ttsVoice = new TtsVoice();
        ttsVoice.setOriginText(content);
        ttsVoice.setVoice(tts);
        ttsVoice.setAge(age);
        ttsVoice.setType(Integer.valueOf(ObjectTypeEnum.fromCode(type).getValue()));
        ttsVoice.setPath(babyName);
        ttsVoice.setCreatedAt(new Date());
        return ttsVoice;
    }

    public TtsVoice getVoiceById(Long id) {
        TtsVoice ttsVoice = ttsVoiceMapper.getById(id);
        return ttsVoice;
    }
}
