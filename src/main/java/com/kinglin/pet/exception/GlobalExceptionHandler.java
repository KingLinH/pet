package com.kinglin.pet.exception;

import com.kinglin.pet.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huangjl
 * @description 自定义异常处理
 * @since 2023-05-12 15:03
 */
@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @param request
     * @param exception
     * @return Result<?>
     * @description 自定义异常处理
     * @date 2023/5/12 16:50
     */
    @ExceptionHandler(value = PetException.class)
    public Result<?> petExceptionHandler(HttpServletRequest request, PetException exception) {
        log.error("发生业务异常，原因：{}", exception.getMsg());
        return Result.error(exception.code, exception.msg);
    }

    /**
     * @param request
     * @param exception
     * @return Result<?>
     * @description 其他异常处理
     * @date 2023/5/12 16:51
     */
    @ExceptionHandler(value = Exception.class)
    public Result<?> exceptionHandler(HttpServletRequest request, Exception exception) {
        log.error("未知异常，原因是：", exception);
        return Result.error(exception.toString());
    }

}
