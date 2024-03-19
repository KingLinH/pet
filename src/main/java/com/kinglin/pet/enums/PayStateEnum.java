package com.kinglin.pet.enums;

/**
 * @author huangjl
 * @description 支付状态枚举类
 * @since 2024-01-09 13:44
 */
public enum PayStateEnum {
    UNPAID((byte) 0, "未支付"),
    PAID((byte) 1, "已支付"),
    REFUNDED((byte) 2, "已退款");
    private final Byte code;
    private final String value;

    PayStateEnum(Byte code, String value) {
        this.code = code;
        this.value = value;
    }

    public static PayStateEnum enumByCode(Byte code) {
        for (PayStateEnum genderEnum : PayStateEnum.values()) {
            if (genderEnum.code.equals(code)) {
                return genderEnum;
            }
        }
        return null;
    }

    public static PayStateEnum enumByValue(String value) {
        for (PayStateEnum genderEnum : PayStateEnum.values()) {
            if (genderEnum.value.equals(value)) {
                return genderEnum;
            }
        }
        return null;
    }

    public Byte getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
