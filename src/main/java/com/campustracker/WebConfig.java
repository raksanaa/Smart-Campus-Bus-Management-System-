package com.campustracker;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global configuration to enable Cross-Origin Resource Sharing (CORS).
 * This allows the frontend (loaded from the local file system or a different port)
 * to communicate with the Spring Boot backend.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allows all origins, methods (GET, POST, etc.), and headers to access all endpoints (/**).
        // This is necessary for local development when the frontend is loaded outside of the server.
        registry.addMapping("/**")
                .allowedOrigins("*") 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
