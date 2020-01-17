package com.ifchange.rpc.position.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName Swagger
 * @Description: 文档工具
 * @Author Yung
 * @Date 2020/1/17
 * @Version V1.0
 **/
@Configuration
@EnableSwagger2
public class Swagger {
    // http://localhost:81/swagger-ui.html
    // Swagger2默认将所有的Controller中的RequestMapping方法都会暴露，
    // 然而在实际开发中，我们并不一定需要把所有API都提现在文档中查看，这种情况下，使用注解
    // @ApiIgnore来解决，如果应用在Controller范围上，则当前Controller中的所有方法都会被忽略，
    // 如果应用在方法上，则对应用的方法忽略暴露API

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ifchange.rpc.position.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @MethodName: apiInfo
     * @Description: 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     * @Param: []
     * @Return: springfox.documentation.service.ApiInfo
     * @Author: Yung
     * @Date: 2020/1/17
     **/
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("Position")
                // 创建人
                .contact(new Contact("yung", "http://github.com", "xin.yang@ifchange.com"))
                // 描述
                .description("职位基础数据相关接口")
                .termsOfServiceUrl("gitlab.ifchange.com")
                // 版本号
                .version("1.0")
                .build();
    }
}
