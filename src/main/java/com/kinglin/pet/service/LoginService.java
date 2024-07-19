package com.kinglin.pet.service;

import com.kinglin.pet.model.LoginUser;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.util.RedisUtil;
import com.kinglin.pet.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author huangjl
 * @description
 * @since 2024-03-19 18:48
 */
@Service
public class LoginService {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    public Result<Map<String, String>> login(LoginUser loginUser) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword());
        Authentication authenticate = authenticationProvider.authenticate(authenticationToken);

        // 没有认证通过
        if (Objects.isNull(authenticate)) {
            throw new BadCredentialsException("访问拒绝：用户名或密码错误");
        }

        // 认证通过，使用用户id生产一个jwt，jwt存入返回值中
        LoginUser principal = (LoginUser) authenticate.getPrincipal();
        // Owner owner = principal.getOwner();
        String token = tokenUtil.generateLoginToken(String.valueOf(principal.getId()), null);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        // 把完整的用户信息存入redis
        redisUtil.setExObjectValue("security:" + principal.getId(), principal, 60, TimeUnit.SECONDS);
        return Result.success(map);
    }

    public Result<String> logout() {
        // 获取SecurityContextHolder中的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        redisUtil.removeCache("security:" + principal.getId());
        return Result.success("退出成功");
    }
}
