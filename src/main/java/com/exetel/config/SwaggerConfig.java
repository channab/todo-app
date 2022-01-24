package com.exetel.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(generateAPIInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.exetel.controller"))
            .paths(PathSelectors.any())
            .build();
    }

    //Api information
    private ApiInfo generateAPIInfo() {
        return new ApiInfo("TOdo API", "API for todo operations", "1.0.0",
            "https://extel.com", getContacts(), "", "", new ArrayList());
    }

    // Developer Contacts
    private Contact getContacts() {
        return new Contact("Channa Bandara", "", "abeetha87@gmail.com");
    }
}
