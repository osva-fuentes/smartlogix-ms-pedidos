package com.proyectofullstack.pedidos.Service;

import com.proyectofullstack.pedidos.client.InventarioClient;
import com.proyectofullstack.pedidos.dto.PedidoRequestDTO;
import com.proyectofullstack.pedidos.dto.PedidoResponseDTO;
import com.proyectofullstack.pedidos.dto.ProductoResponseDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PedidoFacade {

    private final InventarioClient inventarioClient;

    PedidoFacade(InventarioClient inventarioClient) {
        this.inventarioClient = inventarioClient;
    }

    /**
     * Patrón Facade — oculta la complejidad de coordinar:
     * 1. Consultar el producto real en ms-inventario (RestTemplate)
     * 2. Calcular el costo de envío
     * 3. Armar la respuesta final combinando ambos datos
     */
    public PedidoResponseDTO procesarPedido(PedidoRequestDTO requestDTO) {

        log.info("Facade [1/3]: Consultando producto ID {} en ms-inventario...", requestDTO.getProductoId());

        ProductoResponseDTO producto = inventarioClient.buscarProductoPorId(requestDTO.getProductoId());

        log.info("Facade [2/3]: Calculando costo de envío tipo '{}'...", requestDTO.getTipoEnvio());
        String costoEnvio = requestDTO.getTipoEnvio().equalsIgnoreCase("express") ? "$5990" : "$2990";

        log.info("Facade [3/3]: Registrando pedido para cliente '{}'...", requestDTO.getNombreCliente());

        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setNombreCliente(requestDTO.getNombreCliente());
        response.setTipoEnvio(requestDTO.getTipoEnvio());
        response.setCostoEnvio(costoEnvio);
        response.setProducto(producto);
        response.setResumenPedido(String.format(
                "Pedido procesado | Cliente: %s | Producto: %s (Stock: %s) | Envío %s: %s",
                requestDTO.getNombreCliente(),
                producto.getNombre(),
                producto.getEstadoStock(),
                requestDTO.getTipoEnvio(),
                costoEnvio
        ));

        log.info("Facade completado: {}", response.getResumenPedido());
        return response;
    }
}