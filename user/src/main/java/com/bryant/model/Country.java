package com.bryant.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Country implements Serializable {
    private Long id;
    private String name;
    private String position;
}
