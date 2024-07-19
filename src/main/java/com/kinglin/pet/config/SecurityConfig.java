package com.kinglin.pet.config;

import com.kinglin.pet.filter.JwtAuthenticationTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author huangjl
 * @description
 * @since 2024-03-19 18:26
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                // 从authentication中获取用户名和身份凭证
                String username = authentication.getName();
                String password = authentication.getCredentials().toString();

                UserDetails loginUser = userDetailsService.loadUserByUsername(username);
                if (Objects.isNull(loginUser) || !passwordEncoder().matches(password, loginUser.getPassword())) {
                    log.error("访问拒绝：用户名或密码错误");
                    throw new BadCredentialsException("用户名或密码错误！");
                }
                log.info("访问成功：" + loginUser);
                return new UsernamePasswordAuthenticationToken(loginUser, password, loginUser.getAuthorities());
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        };
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 不使用session存储信息
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 前后端分离项目，使用自定义的token
        http.csrf().disable();
        // 配置异常捕获
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);
        http.authorizeRequests()
                .antMatchers("/api/pet/auth/login","/api/pet/owner").anonymous()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
        // 把jwt认证过滤器放到用户名密码认证前面
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 资源放行
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 放行静态资源
        return (web) -> web.ignoring().antMatchers(
                "/css/",
                "/js/",
                "/index.html",
                "/favicon.ico",
                "/doc.html",
                "/swagger-resources/**",
                "/webjars/**",
                "/v3/api-docs/**",
                "/v2/api-docs/**",
                "/swagger-ui.html");
    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("abc123...");
        System.out.println(encode);
    }

}
