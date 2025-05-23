package cl.duocuc.perfulandia.PerfulandiaSPA.controller;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Pedido;
import cl.duocuc.perfulandia.PerfulandiaSPA.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<Pedido> crearPedido(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(pedidoService.crearPedido(clienteId));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> obtenerPedidosCliente(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(pedidoService.listarPedidosPorCliente(clienteId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(pedidoService.buscarPedidosPorEstado(estado));
    }

    @GetMapping("/rango-precio")
    public ResponseEntity<List<Pedido>> obtenerPorRangoPrecio(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(pedidoService.buscarPedidosPorRangoPrecio(min, max));
    }

    @PutMapping("/{pedidoId}/estado/{nuevoEstado}")
    public ResponseEntity<Pedido> actualizarEstado(
            @PathVariable Integer pedidoId,
            @PathVariable String nuevoEstado) {
        return ResponseEntity.ok().build();
    }
}