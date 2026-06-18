package com.proyectofullstack.pedidos.dto;

// DTO de ENTRADA para crear un pedido.
// El frontend envía estos datos — nunca envía el objeto Producto completo.
public class PedidoRequestDTO {

    private Long productoId;
    private String tipoEnvio;
    private String nombreCliente;

    // Constructor vacío requerido por Jackson
    public PedidoRequestDTO() {}

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public String getTipoEnvio() { return tipoEnvio; }
    public void setTipoEnvio(String tipoEnvio) { this.tipoEnvio = tipoEnvio; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
}
