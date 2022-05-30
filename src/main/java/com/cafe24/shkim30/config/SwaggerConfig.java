package com.cafe24.shkim30.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cafe24.shkim30.domain.Member;
import com.cafe24.shkim30.dto.MemberDTO;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // => ���ڸ����̴� ��... �ȵǴ°�
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket restAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
        		//.ignoredParameterTypes(MemberDTO.class)
        		.useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cafe24.shkim30"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SH ��α� REST API")
                .version("1.0.0")
                .description("������ SH�� ��α� swagger api �Դϴ�.")
                .build();
    }
}