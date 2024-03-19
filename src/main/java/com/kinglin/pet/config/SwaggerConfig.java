package com.kinglin.pet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author huangjl
 * @description swagger配置文件
 * @since 2023-05-12 14:18
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final Contact DEFAULT_CONTACT = new Contact("huangjl", "http://localhost:7777", "hjinlin@foxmail.com");

    @Bean
    public Docket docket(Environment env) {
        Profiles dev = Profiles.of("dev");
        boolean accepted = env.acceptsProfiles(dev);
        return new Docket(DocumentationType.SWAGGER_2).enable(accepted).apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "《城宠万千》宠物服务预约API文档",
                "该文档用于测试API接口",
                "1.0.0",
                "urn:tos",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
        );
    }

}
