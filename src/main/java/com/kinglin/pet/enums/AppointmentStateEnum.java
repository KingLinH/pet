package com.kinglin.pet.enums;

/**
 * @author huangjl
 * @description 预约状态枚举类
 * @since 2024-01-09 13:44
 */
public enum AppointmentStateEnum {
    PENDING((byte) 0, "预约中", "Pending"),
    CONFIRMED((byte) 1, "已完成", "Confirmed"),
    CANCELLED((byte) 2, "已取消", "cancelled");
    private final Byte code;
    private final String name;
    private final String value;

    AppointmentStateEnum(Byte code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

    public static AppointmentStateEnum enumByCode(Byte code) {
        for (AppointmentStateEnum genderEnum : AppointmentStateEnum.values()) {
            if (genderEnum.code.equals(code)) {
                return genderEnum;
            }
        }
        return null;
    }

    public static AppointmentStateEnum enumByName(String name) {
        for (AppointmentStateEnum genderEnum : AppointmentStateEnum.values()) {
            if (genderEnum.name.equals(name)) {
                return genderEnum;
            }
        }
        return null;
    }

    public static AppointmentStateEnum enumByValue(String value) {
        for (AppointmentStateEnum genderEnum : AppointmentStateEnum.values()) {
            if (genderEnum.value.equals(value)) {
                return genderEnum;
            }
        }
        return null;
    }

    public Byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
