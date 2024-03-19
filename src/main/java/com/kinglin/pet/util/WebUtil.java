package com.kinglin.pet.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huangjl
 * @description
 * @since 2024-03-19 19:24
 */
@Slf4j
public class WebUtil {

    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            log.error("renderString异常 ", e);
        }
        return null;
    }

}
