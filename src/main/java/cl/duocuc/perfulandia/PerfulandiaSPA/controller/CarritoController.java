package cl.duocuc.perfulandia.PerfulandiaSPA.controller;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Carrito;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Cliente;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Productos;
import cl.duocuc.perfulandia.PerfulandiaSPA.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    private final CarritoService carritoService;

    @Autowired
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Carrito>> obtenerCarrito(@PathVariable Integer clienteId) {
        try {
            List<Carrito> carrito = carritoService.obtenerCarritoPorCliente(clienteId);
            return ResponseEntity.ok(carrito);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<Carrito> agregarAlCarrito(
            @RequestParam Integer clienteId,
            @RequestParam Integer productoId,
            @RequestParam int cantidad) {
        try {
            if (cantidad <= 0) {
                return ResponseEntity.badRequest().build();
            }

            Cliente cliente = new Cliente();
            cliente.setIdusuario(clienteId);

            Productos producto = new Productos();
            producto.setIdproducto(productoId);

            Carrito resultado = carritoService.agregarAlCarrito(cliente, producto, cantidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/actualizar/{carritoId}")
    public ResponseEntity<Carrito> actualizarCantidad(
            @PathVariable Integer carritoId,
            @RequestParam int cantidad) {
        try {
            if (cantidad <= 0) {
                return ResponseEntity.badRequest().build();
            }
            
            Carrito resultado = carritoService.actualizarCantidad(carritoId, cantidad);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarItem(
            @RequestParam Integer clienteId,
            @RequestParam Integer productoId) {
        try {
            carritoService.eliminarItemCarrito(clienteId, productoId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/total/{clienteId}")
    public ResponseEntity<BigDecimal> calcularTotal(@PathVariable Integer clienteId) {
        try {
            BigDecimal total = carritoService.calcularTotal(clienteId);
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/vaciar/{clienteId}")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Integer clienteId) {
        try {
            carritoService.vaciarCarrito(clienteId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}