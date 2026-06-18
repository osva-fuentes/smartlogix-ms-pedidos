package com.proyectofullstack.pedidos.Controller;

import com.proyectofullstack.pedidos.Service.PedidoFacade;
import com.proyectofullstack.pedidos.dto.PedidoRequestDTO;
import com.proyectofullstack.pedidos.dto.PedidoResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoFacade facade;

    PedidoController(PedidoFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/procesar")
    public ResponseEntity<PedidoResponseDTO> procesarPedido(@RequestBody PedidoRequestDTO requestDTO) {
        PedidoResponseDTO resultado = facade.procesarPedido(requestDTO);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("ms-pedidos activo - Facade Pattern + RestTemplate operativo");
    }
}