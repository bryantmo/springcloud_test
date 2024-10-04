package com.bryant.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Users {

    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long tenantId;

}
