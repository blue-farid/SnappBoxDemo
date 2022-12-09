package com.snapp.boxdemo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class BoxDemoConfiguration {
    @Bean
    public Docket apiDoc() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.snapp.boxdemo.controller.api"))
                .paths(PathSelectors.ant("/api/order/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(
                        HttpMethod.GET,
                        java.util.List.of(
                                new ResponseBuilder()
                                        .code("200")
                                        .description("OK")
                                        .build(),
                                new ResponseBuilder()
                                        .code("400")
                                        .description("Bad Request")
                                        .build(),
                                new ResponseBuilder()
                                        .code("404")
                                        .description("Not Found")
                                        .build(),
                                new ResponseBuilder()
                                        .code("500")
                                        .description("Internal Server Error")
                                        .build()
                        )
                );

    }
/*    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider memorySwaggerResourcesProvider) {
        return () -> {
            SwaggerResource wsResource = new SwaggerResource();
            wsResource.setName("Box Demo Application");
            wsResource.setSwaggerVersion("3.0.0");
            wsResource.setLocation("/swagger-config.yml");
            List<SwaggerResource> resources = new ArrayList<>(memorySwaggerResourcesProvider.get());
            resources.clear();
            resources.add(wsResource);
            return resources;
        };
    }*/

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
