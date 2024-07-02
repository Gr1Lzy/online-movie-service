package com.github.onlinemovieservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${frontend.uri}")
    private String frontendUri;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configure CORS globally here
        registry.addMapping("/**")
                .allowedOrigins(frontendUri)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}