package com.kinglin.pet.enums;

import com.kinglin.pet.exception.BaseErrorInfoInterface;

/**
 * @author huangjl
 * @description 异常处理枚举
 * @since 2023-05-12 16:16
 */
public enum ExceptionEnum implements BaseErrorInfoInterface {

    // 数据操作错误定义
    SUCCESS("200", "成功"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误");

    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误描述
     */
    private final String msg;

    ExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
