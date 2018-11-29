package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
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
 * Created by T011689 on 2018/11/27.
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value(value = "${swagger.swaggerSwitch}")
     private boolean swaggerSwitch;
    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.web"))
                .paths(PathSelectors.regex("/api/.*"))
                .build();
        if(swaggerSwitch){
            docket.enable(true);
        }else {
            docket.enable(false);
        }
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("demo系统API")
                .description("demo系统API")
                .termsOfServiceUrl("http://127.0.0.1:8080/")
                .contact(new Contact("zhaodong",null,null))
                .version("1.0")
                .build();
    }

}
