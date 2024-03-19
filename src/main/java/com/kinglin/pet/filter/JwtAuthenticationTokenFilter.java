package com.kinglin.pet.filter;

import com.kinglin.pet.model.LoginUser;
import com.kinglin.pet.util.StringUtils;
import com.kinglin.pet.util.TokenUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author huangjl
 * @description
 * @since 2024-03-19 19:43
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private RedisUtil redisUtil;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token
        boolean verifyToken = tokenUtil.verifyToken(token);
        if (!verifyToken) {
            log.error("当前token已过期");
            throw new AuthenticationException("当前token已过期");
        }
        String id = tokenUtil.getIdFromToken();

        // 从redis中获取用户信息
        LoginUser loginUser = redisUtil.getObject("security:" + id, LoginUser.class);
        if (Objects.isNull(loginUser)) {
            log.error("用户未登录");
            throw new AuthenticationException("用户未登录");
        }
        // 更新存活时间
        redisUtil.setExpire("security:" + id, 60);

        // 存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
