package com.ace.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Classname: swagger3Config
 * @Date: 18/3/2024 7:09 am
 * @Author: garlam
 * @Description:
 */

@Configuration
public class swagger3Config {
    private static final Logger log = LogManager.getLogger(swagger3Config.class.getName());

    @Bean
    public GroupedOpenApi PayApi()
    {
        return GroupedOpenApi.builder().group("支付微服务模块").pathsToMatch("/pay/**").build();
    }
    @Bean
    public GroupedOpenApi OtherApi()
    {
        return GroupedOpenApi.builder().group("其它微服务模块").pathsToMatch("/other/**", "/others").build();
    }
    /*@Bean
    public GroupedOpenApi CustomerApi()
    {
        return GroupedOpenApi.builder().group("客户微服务模块").pathsToMatch("/customer/**", "/customers").build();
    }*/

    @Bean
    public OpenAPI docsOpenApi()
    {
        return new OpenAPI()
                .info(new Info().title("Ace Cloud Application")
                .description("通用设计rest")
                .version("v1.0"))
                .externalDocs(new ExternalDocumentation()
                .description("www.ace.com")
                .url("https://www.ace.com"));
        // http://localhost:8001/swagger-ui/index.html
    }

}

