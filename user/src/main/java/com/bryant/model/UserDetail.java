package com.bryant.model;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetail implements Serializable {

    private static final long serialVersionUID = 2235541748764244156L;

    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long tenantId;

}
