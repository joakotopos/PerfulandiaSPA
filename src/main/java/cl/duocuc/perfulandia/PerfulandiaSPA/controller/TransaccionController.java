package cl.duocuc.perfulandia.PerfulandiaSPA.controller;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.TransaccionPago;
import cl.duocuc.perfulandia.PerfulandiaSPA.service.TransaccionPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionPagoService transaccionPagoService;

    @GetMapping
    public ResponseEntity<List<TransaccionPago>> obtenerTodasLasTransacciones() {
        return ResponseEntity.ok(transaccionPagoService.obtenerTodasLasTransacciones());
    }

    @GetMapping("/metodo/{metodoPago}")
    public ResponseEntity<List<TransaccionPago>> obtenerTransaccionesPorMetodo(
            @PathVariable TransaccionPago.MetodoPago metodoPago) {
        return ResponseEntity.ok(transaccionPagoService.obtenerTransaccionesPorMetodoPago(metodoPago));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<TransaccionPago> obtenerTransaccionPorPedido(@PathVariable Integer pedidoId) {
        return transaccionPagoService.obtenerTransaccionPorPedido(pedidoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ventas")
    public ResponseEntity<BigDecimal> obtenerVentasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(transaccionPagoService.calcularVentasPorPeriodo(inicio, fin));
    }

    @GetMapping("/ventas/dia")
    public ResponseEntity<BigDecimal> obtenerVentasDelDia() {
        return ResponseEntity.ok(transaccionPagoService.obtenerVentasDelDia());
    }

    @GetMapping("/ventas/mes")
    public ResponseEntity<BigDecimal> obtenerVentasDelMes() {
        return ResponseEntity.ok(transaccionPagoService.obtenerVentasDelMes());
    }

    @PatchMapping("/{idTransaccion}/estado")
    public ResponseEntity<TransaccionPago> actualizarEstado(
            @PathVariable String idTransaccion,
            @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(transaccionPagoService.actualizarEstado(idTransaccion, nuevoEstado));
    }

    @PostMapping("/{idTransaccion}/procesar")
    public ResponseEntity<TransaccionPago> procesarPago(@PathVariable String idTransaccion) {
        return ResponseEntity.ok(transaccionPagoService.procesarPago(idTransaccion));
    }

    @PostMapping("/{idTransaccion}/cancelar")
    public ResponseEntity<TransaccionPago> cancelarTransaccion(@PathVariable String idTransaccion) {
        return ResponseEntity.ok(transaccionPagoService.cancelarTransaccion(idTransaccion));
    }
}