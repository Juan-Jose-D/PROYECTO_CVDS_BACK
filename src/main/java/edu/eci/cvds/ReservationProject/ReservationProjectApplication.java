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
    public static class CorsConfig { // Clase interna estática

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**") // Permite CORS en todas las rutas
                            .allowedOrigins("http://127.0.0.1:5500", "http://localhost:3000") // Origen permitido (cambia esto al origen de tu frontend) 
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                            .allowedHeaders("*") // Cabeceras permitidas
                            .allowCredentials(true); // Permite credenciales (cookies, tokens, etc.)
                }
            };
        }
    }
}
