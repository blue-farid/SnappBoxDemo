package com.snapp.boxdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class BoxDemoConfiguration {
    @Bean
    public Docket apiDoc() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .paths(PathSelectors.none()).build();
    }
    @Primary
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
    }
}
