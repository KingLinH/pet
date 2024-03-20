package com.kinglin.pet.handler;

import cn.hutool.json.JSONUtil;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.util.WebUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huangjl
 * @description 认证异常
 * @since 2024-03-19 19:23
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = new Result("500", "用户认证失败" + authException.getLocalizedMessage());
        WebUtil.renderString(response, JSONUtil.toJsonStr(result));
    }
}
