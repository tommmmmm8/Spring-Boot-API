package com.tom.footballmanagement;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**") // for all endpoints
                .allowedOrigins("http://localhost:63343")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "HEAD")
//                .allowedHeaders()
//                .exposedHeaders()
                .allowCredentials(true).maxAge(3600);
    }
}
