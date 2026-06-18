package com.proyectofullstack.pedidos.client;

import com.proyectofullstack.pedidos.dto.ProductoResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class InventarioClient {

    private static final String TOKEN_SECRETO = "SmartLogix-Token-2024";

    private final RestTemplate restTemplate;
    private final String inventarioServiceUrl;

    public InventarioClient(
            RestTemplate restTemplate,
            @Value("${inventario.service.url}") String inventarioServiceUrl) {
        this.restTemplate = restTemplate;
        this.inventarioServiceUrl = inventarioServiceUrl;
    }

    public ProductoResponseDTO buscarProductoPorId(Long productoId) {
        String url = inventarioServiceUrl + "/api/inventario/" + productoId;

        try {
            // Agregamos el header Authorization que exige el ApiGatewayFilter de ms-inventario
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", TOKEN_SECRETO);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<ProductoResponseDTO> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, ProductoResponseDTO.class);

            ProductoResponseDTO producto = response.getBody();

            if (producto == null) {
                throw new RuntimeException("ms-inventario respondió vacío para producto " + productoId);
            }

            return producto;

        } catch (HttpStatusCodeException ex) {
            throw new RuntimeException("No se pudo obtener el producto " + productoId
                    + ". ms-inventario respondió: " + ex.getStatusCode());

        } catch (ResourceAccessException ex) {
            throw new RuntimeException("ms-pedidos no pudo conectarse con ms-inventario en: " + url);
        }
    }
}