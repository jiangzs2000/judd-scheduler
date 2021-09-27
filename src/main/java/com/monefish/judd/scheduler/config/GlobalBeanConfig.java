package com.monefish.judd.scheduler.config;

import com.monefish.judd.scheduler.config.interceptors.GlobalAspectInteceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.UnknownHostException;

@Configuration
@EnableSwagger2
@RefreshScope
public class GlobalBeanConfig {

    @Value("${server.port}")
    private int serverPort;
    @Bean(name = "loggerInteceptor")
    public GlobalAspectInteceptor getLoggerInteceptor() {
        return new GlobalAspectInteceptor();
    }

    @Bean
    public Docket createRestApi()  throws UnknownHostException{
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo( getApiInfo() )
                .host("localhost:"+serverPort)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.monefish.judd.scheduler"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("judd-scheduler")
                .description("scheduler渠道网关")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}