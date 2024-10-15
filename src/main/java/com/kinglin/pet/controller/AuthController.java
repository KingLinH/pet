package com.kinglin.pet.controller;

import com.kinglin.pet.common.Constant;
import com.kinglin.pet.model.LoginUser;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付宝
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@RestController
@RequestMapping(Constant.API_NAME + "/auth")
public class AuthController {
    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public Result<Map<String, String>> login(LoginUser loginUser) {
        return loginService.login(loginUser);
    }

    @PostMapping("/logout")
    public Result<String> logout() {
        return loginService.logout();
    }

}
