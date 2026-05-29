package com.javanauta.usuario.infrastructure.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer configCors(){
        return new WebMvcConfigurer() {
            public void addCorsMapping (CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "PATCH","DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(360);
            }
        };
    }
}
