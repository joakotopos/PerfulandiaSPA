package cl.duocuc.perfulandia.PerfulandiaSPA.controller;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Cliente;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Productos;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Resena;
import cl.duocuc.perfulandia.PerfulandiaSPA.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@CrossOrigin(origins = "*")

public class ResenaController {
    private final ResenaService resenaService;

    @Autowired
    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Resena> crearResena(
            @RequestParam Integer clienteId,
            @RequestParam Integer productoId,
            @RequestParam String comentario,
            @RequestParam Integer calificacion) {
        try {
            Cliente cliente = new Cliente();
            cliente.setIdusuario(clienteId);

            Productos producto = new Productos();
            producto.setIdproducto(productoId);

            Resena resena = resenaService.crearOActualizarResena(cliente, producto, comentario, calificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(resena);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Resena>> obtenerResenasPorProducto(@PathVariable Integer productoId) {
        List<Resena> resenas = resenaService.obtenerResenasPorProducto(productoId);
        return ResponseEntity.ok(resenas);
    }

    @GetMapping("/promedio/{productoId}")
    public ResponseEntity<Double> obtenerPromedioCalificaciones(@PathVariable Integer productoId) {
        Double promedio = resenaService.obtenerPromedioCalificaciones(productoId);
        return ResponseEntity.ok(promedio);
    }

    @GetMapping("/verificar")
    public ResponseEntity<Boolean> existeResena(
            @RequestParam Integer clienteId,
            @RequestParam Integer productoId) {
        boolean existe = resenaService.existeResena(clienteId, productoId);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Resena> obtenerResena(
            @RequestParam Integer clienteId,
            @RequestParam Integer productoId) {
        return resenaService.obtenerResena(clienteId, productoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{resenaId}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Integer resenaId) {
        try {
            resenaService.eliminarResena(resenaId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Resena>> obtenerTodasResenas() {
        List<Resena> resenas = resenaService.obtenerTodasResenas();
        return ResponseEntity.ok(resenas);
    }

    @PatchMapping("/{resenaId}/comentario")
    public ResponseEntity<Resena> actualizarComentario(
            @PathVariable Integer resenaId,
            @RequestParam String nuevoComentario) {
        try {
            Resena resena = resenaService.actualizarComentario(resenaId, nuevoComentario);
            return ResponseEntity.ok(resena);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{resenaId}/calificacion")
    public ResponseEntity<Resena> actualizarCalificacion(
            @PathVariable Integer resenaId,
            @RequestParam Integer nuevaCalificacion) {
        try {
            Resena resena = resenaService.actualizarCalificacion(resenaId, nuevaCalificacion);
            return ResponseEntity.ok(resena);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{resenaId}/verificar-cliente/{clienteId}")
    public ResponseEntity<Boolean> esResenaDeCliente(
            @PathVariable Integer resenaId,
            @PathVariable Integer clienteId) {
        boolean esDelCliente = resenaService.esResenaDeCliente(resenaId, clienteId);
        return ResponseEntity.ok(esDelCliente);
    }
}