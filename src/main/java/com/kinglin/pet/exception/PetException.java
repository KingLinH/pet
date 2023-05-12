package com.kinglin.pet.exception;

/**
 * @author huangjl
 * @description 自定义异常类
 * @since 2023-05-12 16:20
 */
public class PetException extends RuntimeException {

    protected String code;

    protected String msg;

    public PetException() {
        super();
    }

    public PetException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface.getCode());
        this.code = errorInfoInterface.getCode();
        this.msg = errorInfoInterface.getMsg();
    }

    public PetException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface.getCode(), cause);
        this.code = errorInfoInterface.getCode();
        this.msg = errorInfoInterface.getMsg();
    }

    public PetException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public PetException(String code, String msg) {
        super(code);
        this.code = code;
        this.msg = msg;
    }

    public PetException(String code, String msg, Throwable cause) {
        super(code, cause);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
