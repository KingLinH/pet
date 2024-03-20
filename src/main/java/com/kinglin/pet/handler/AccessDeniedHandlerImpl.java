package com.kinglin.pet.handler;

import cn.hutool.json.JSONUtil;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.util.WebUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huangjl
 * @description
 * @since 2024-03-20 10:17
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result result = new Result("500", "用户认证失败" + accessDeniedException.getLocalizedMessage());
        WebUtil.renderString(response, JSONUtil.toJsonStr(result));
    }
}
