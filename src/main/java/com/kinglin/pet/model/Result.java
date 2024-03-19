package com.kinglin.pet.model;

import com.alibaba.fastjson2.JSONObject;
import com.kinglin.pet.enums.ExceptionEnum;
import com.kinglin.pet.exception.BaseErrorInfoInterface;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huangjl
 * @description 统一结果返回
 * @since 2023-05-12 16:10
 */
@Data
@ApiModel("统一结果返回")
public class Result<T> {

    @ApiModelProperty("返回码")
    private String code;

    @ApiModelProperty("返回消息")
    private String msg;

    @ApiModelProperty("返回数据")
    private T data;

    public Result() {
    }

    public Result(BaseErrorInfoInterface errorInfo) {
        this.code = errorInfo.getCode();
        this.msg = errorInfo.getMsg();
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ExceptionEnum.SUCCESS.getCode());
        result.setMsg(ExceptionEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(BaseErrorInfoInterface errorInfo) {
        Result<T> result = new Result<>();
        result.setCode(errorInfo.getCode());
        result.setMsg(errorInfo.getMsg());
        result.setData(null);
        return result;
    }

    public static <T> Result<T> error(String code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode("-1");
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
