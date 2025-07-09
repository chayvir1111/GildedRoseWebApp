package com.gildedrose;

import com.gildedrose.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GildedRoseWebApplication implements CommandLineRunner {

    @Autowired
    private ItemTypeService itemTypeService;

    public static void main(String[] args) {
        SpringApplication.run(GildedRoseWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize default item types on startup
        itemTypeService.initializeDefaultItemTypes();
        System.out.println("Gilded Rose Web Application started successfully!");
        System.out.println("Admin Panel: http://localhost:8080/admin");
        System.out.println("Calendar View: http://localhost:8080/calendar");
        System.out.println("API Documentation: http://localhost:8080/api");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
