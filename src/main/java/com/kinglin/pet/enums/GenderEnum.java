package com.kinglin.pet.enums;

/**
 * @author huangjl
 * @description 性别枚举类
 * @since 2024-01-09 13:44
 */
public enum GenderEnum {
    MALE(1, "男", "Male"),
    FEMALE(2, "女", "Female"),
    OTHER(3, "其他", "Other");
    private final Integer code;
    private final String name;
    private final String value;

    GenderEnum(Integer code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

    public static GenderEnum enumByCode(Integer code) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.code.equals(code)) {
                return genderEnum;
            }
        }
        return OTHER;
    }

    public static GenderEnum enumByName(String name) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.name.equals(name)) {
                return genderEnum;
            }
        }
        return OTHER;
    }

    public static GenderEnum enumByValue(String value) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.value.equals(value)) {
                return genderEnum;
            }
        }
        return OTHER;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
