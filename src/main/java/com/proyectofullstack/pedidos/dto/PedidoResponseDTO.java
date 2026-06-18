package com.proyectofullstack.pedidos.dto;

// DTO de SALIDA para un pedido procesado.
// Combina datos locales del pedido con datos del producto
// obtenidos desde ms-inventario via RestTemplate.
// Esto demuestra la comunicación entre microservicios.
public class PedidoResponseDTO {

    private String nombreCliente;
    private String tipoEnvio;
    private String costoEnvio;
    private String resumenPedido;

    // Este campo viene de ms-inventario via RestTemplate
    // No sale de la tabla de ms-pedidos
    private ProductoResponseDTO producto;

    // Constructor vacío requerido por Jackson
    public PedidoResponseDTO() {}

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getTipoEnvio() { return tipoEnvio; }
    public void setTipoEnvio(String tipoEnvio) { this.tipoEnvio = tipoEnvio; }

    public String getCostoEnvio() { return costoEnvio; }
    public void setCostoEnvio(String costoEnvio) { this.costoEnvio = costoEnvio; }

    public String getResumenPedido() { return resumenPedido; }
    public void setResumenPedido(String resumenPedido) { this.resumenPedido = resumenPedido; }

    public ProductoResponseDTO getProducto() { return producto; }
    public void setProducto(ProductoResponseDTO producto) { this.producto = producto; }
}