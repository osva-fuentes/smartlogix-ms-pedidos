package com.proyectofullstack.pedidos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Crea un Bean de RestTemplate disponible para toda la aplicación.
 * Spring lo inyectará automáticamente donde sea necesario.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}