package edu.eci.cvds.ReservationProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ReservationProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationProjectApplication.class, args);
    }

    @Configuration
    public static class CorsConfig {

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOriginPatterns(
                                "https://*.azurewebsites.net",
                                "http://localhost:[*]",
                                "http://127.0.0.1:[*]"
                            )
                            .allowedOrigins("http://127.0.0.1:5500", "http://localhost:3000", "https://frontdespliegue-gae9f9b2aaedfabw.eastus-01.azurewebsites.net")  
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") 
                            .allowedHeaders("*")
                            .exposedHeaders("Authorization") 
                            .allowCredentials(true);
                }
            };
        }
    }
}
