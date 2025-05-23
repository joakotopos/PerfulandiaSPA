package cl.duocuc.perfulandia.PerfulandiaSPA.controller;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Proveedor;
import cl.duocuc.perfulandia.PerfulandiaSPA.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")



public class ProveedorController {

    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PostMapping
    public ResponseEntity<Proveedor> crearProveedor(@RequestBody Proveedor proveedor) {
        try {
            Proveedor nuevoProveedor = proveedorService.crearProveedor(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProveedor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> obtenerTodosProveedores() {
        List<Proveedor> proveedores = proveedorService.obtenerTodosProveedores();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerProveedorPorId(@PathVariable Integer id) {
        return proveedorService.obtenerProveedorPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/con-productos")
    public ResponseEntity<List<Proveedor>> obtenerProveedoresConProductos() {
        List<Proveedor> proveedores = proveedorService.obtenerProveedoresConProductos();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Proveedor>> buscarProveedoresPorNombre(@RequestParam String nombre) {
        try {
            List<Proveedor> proveedores = proveedorService.buscarProveedoresPorNombre(nombre);
            return ResponseEntity.ok(proveedores);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<Proveedor> encontrarProveedorPorProducto(@PathVariable Integer productoId) {
        return proveedorService.encontrarProveedorPorProducto(productoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(
            @PathVariable Integer id,
            @RequestBody Proveedor proveedorActualizado) {
        try {
            Proveedor proveedor = proveedorService.actualizarProveedor(id, proveedorActualizado);
            return ResponseEntity.ok(proveedor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Integer id) {
        try {
            proveedorService.eliminarProveedor(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/contacto")
    public ResponseEntity<Proveedor> actualizarContactoProveedor(
            @PathVariable Integer id,
            @RequestParam String nuevoContacto) {
        try {
            Proveedor proveedor = proveedorService.actualizarContactoProveedor(id, nuevoContacto);
            return ResponseEntity.ok(proveedor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}