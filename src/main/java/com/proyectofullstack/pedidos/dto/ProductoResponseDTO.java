package com.proyectofullstack.pedidos.dto;

// DTO que representa la respuesta que ms-inventario entrega por HTTP.
// Vive dentro de ms-pedidos porque cada microservicio define
// el contrato que necesita consumir — no importa que el nombre sea similar.
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private Integer stock;
    private Double precio;
    private String estadoStock;

    // Constructor vacío requerido por Jackson para convertir JSON a objeto
    public ProductoResponseDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getEstadoStock() { return estadoStock; }
    public void setEstadoStock(String estadoStock) { this.estadoStock = estadoStock; }
}