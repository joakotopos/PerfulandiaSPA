package cl.duocuc.perfulandia.PerfulandiaSPA.controller;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.CuponDescuento;
import cl.duocuc.perfulandia.PerfulandiaSPA.service.CuponDescuentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cupones")
@CrossOrigin(origins = "*")
public class CuponDescuentoController {

    private final CuponDescuentoService cuponService;

    @Autowired
    public CuponDescuentoController(CuponDescuentoService cuponService) {
        this.cuponService = cuponService;
    }

    @PostMapping
    public ResponseEntity<CuponDescuento> crearCupon(
            @RequestParam String codigo,
            @RequestParam BigDecimal descuento,
            @RequestParam LocalDate fechaExpiracion) {
        try {
            CuponDescuento nuevoCupon = cuponService.crearCuponDescuento(codigo, descuento, fechaExpiracion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCupon);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/validos")
    public ResponseEntity<List<CuponDescuento>> obtenerCuponesValidos() {
        List<CuponDescuento> cupones = cuponService.obtenerCuponesValidos();
        return cupones.isEmpty() 
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(cupones);
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<CuponDescuento> obtenerCuponPorPedido(
            @PathVariable Integer pedidoId) {
        return cuponService.obtenerCuponPorPedido(pedidoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{codigo}/estado")
    public ResponseEntity<Void> cambiarEstadoCupon(
            @PathVariable String codigo,
            @RequestParam boolean activo) {
        try {
            if (activo) {
                cuponService.activarCupon(codigo);
            } else {
                cuponService.desactivarCupon(codigo);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/validar/{codigo}")
    public ResponseEntity<Boolean> validarCupon(@PathVariable String codigo) {
        try {
            boolean esValido = cuponService.validarCupon(codigo);
            return ResponseEntity.ok(esValido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigo}/fecha")
    public ResponseEntity<CuponDescuento> actualizarFechaExpiracion(
            @PathVariable String codigo,
            @RequestParam LocalDate nuevaFecha) {
        try {
            CuponDescuento cuponActualizado = cuponService.actualizarFechaExpiracion(codigo, nuevaFecha);
            return ResponseEntity.ok(cuponActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminarCupon(@PathVariable String codigo) {
        try {
            cuponService.eliminarCupon(codigo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}