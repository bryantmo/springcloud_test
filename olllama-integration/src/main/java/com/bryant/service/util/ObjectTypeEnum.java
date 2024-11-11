package com.bryant.service.util;

public enum ObjectTypeEnum {

    BABY("baby", "宝宝", "1"),
    CHILD("child", "儿童", "2"),
    GIRL("girl", "女孩","3"),
    BOY("boy", "男孩", "4"),
    WOMAN("woman","女士", "5"),
    MAN("man", "男士","6")
    ;

    private String name;
    private String chName;
    private String value;

    ObjectTypeEnum(String name, String chName, String value) {
        this.name = name;
        this.chName = chName;
        this.value = value;
    }

    public static ObjectTypeEnum fromCode(String type) {
        for (ObjectTypeEnum e : values()) {
            if (e.getValue().equals(type)) {
                return e;
            }
        }
        return ObjectTypeEnum.BABY;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getChName() {
        return chName;
    }
}
