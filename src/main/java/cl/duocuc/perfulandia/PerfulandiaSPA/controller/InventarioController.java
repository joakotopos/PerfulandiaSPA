package cl.duocuc.perfulandia.PerfulandiaSPA.controller;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Inventario;
import cl.duocuc.perfulandia.PerfulandiaSPA.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    private final InventarioService inventarioService;

    @Autowired
    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/sucursal/{sucursalId}/con-stock")
    public ResponseEntity<List<Inventario>> obtenerConStock(@PathVariable Integer sucursalId) {
        try {
            List<Inventario> inventario = inventarioService.obtenerProductosConStock(sucursalId);
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/sucursal/{sucursalId}/sin-stock")
    public ResponseEntity<List<Inventario>> obtenerSinStock(@PathVariable Integer sucursalId) {
        try {
            List<Inventario> inventario = inventarioService.obtenerProductosSinStock(sucursalId);
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Inventario> crearRegistroInventario(@RequestBody Inventario inventario) {
        try {
            Inventario nuevoInventario = inventarioService.crearInventario(
                    inventario.getProducto(),
                    inventario.getSucursal(),
                    inventario.getCantidad());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoInventario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{inventarioId}/cantidad")
    public ResponseEntity<Inventario> actualizarCantidad(
            @PathVariable Integer inventarioId,
            @RequestParam int nuevaCantidad) {
        try {
            if (nuevaCantidad < 0) {
                return ResponseEntity.badRequest().build();
            }
            Inventario inventario = inventarioService.actualizarCantidad(inventarioId, nuevaCantidad);
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{sucursalId}/{productoId}/disponibilidad")
    public ResponseEntity<Boolean> verificarDisponibilidad(
            @PathVariable Integer sucursalId,
            @PathVariable Integer productoId,
            @RequestParam int cantidad) {
        try {
            if (cantidad <= 0) {
                return ResponseEntity.badRequest().build();
            }
            boolean disponible = inventarioService.hayStockSuficiente(sucursalId, productoId, cantidad);
            return ResponseEntity.ok(disponible);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{inventarioId}/agregar-stock")
    public ResponseEntity<Void> agregarStock(
            @PathVariable Integer inventarioId,
            @RequestParam int cantidad) {
        try {
            if (cantidad <= 0) {
                return ResponseEntity.badRequest().build();
            }
            inventarioService.agregarStock(inventarioId, cantidad);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{inventarioId}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Integer inventarioId) {
        try {
            inventarioService.eliminarInventario(inventarioId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
