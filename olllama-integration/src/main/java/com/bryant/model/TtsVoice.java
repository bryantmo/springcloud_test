package com.bryant.model;

import lombok.Data;

import java.util.Date;

@Data
public class TtsVoice {

    private Long id;
    private Integer type;
    private Integer age;
    private String path;
    private String originText;
    private String voice;
    private Date createdAt;

}