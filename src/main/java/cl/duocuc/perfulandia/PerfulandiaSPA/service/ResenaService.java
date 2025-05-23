package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Cliente;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Productos;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Resena;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.ResenaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class ResenaService {
    private final ResenaRepository resenaRepository;

    public Resena crearOActualizarResena(Cliente cliente, Productos producto, String comentario, Integer calificacion) {
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }

        Optional<Resena> resenaExistente = resenaRepository.findResenaExistente(
                cliente.getIdusuario(),
                producto.getIdproducto()
        );

        if (resenaExistente.isPresent()) {
            Resena resena = resenaExistente.get();
            resena.setComentario(comentario);
            resena.setCalificacion(calificacion);
            return resenaRepository.save(resena);
        } else {
            Resena nuevaResena = new Resena();
            nuevaResena.setCliente(cliente);
            nuevaResena.setProducto(producto);
            nuevaResena.setComentario(comentario);
            nuevaResena.setCalificacion(calificacion);
            return resenaRepository.save(nuevaResena);
        }
    }

    public List<Resena> obtenerResenasPorProducto(Integer productoId) {
        return resenaRepository.findByProductoOrdenado(productoId);
    }

    public Double obtenerPromedioCalificaciones(Integer productoId) {
        return resenaRepository.getAverageCalificacionProducto(productoId);
    }

    public boolean existeResena(Integer clienteId, Integer productoId) {
        return resenaRepository.existeResena(clienteId, productoId);
    }

    public Optional<Resena> obtenerResena(Integer clienteId, Integer productoId) {
        return resenaRepository.findResenaExistente(clienteId, productoId);
    }

    public void eliminarResena(Integer resenaId) {
        resenaRepository.deleteById(resenaId);
    }

    public List<Resena> obtenerTodasResenas() {
        return resenaRepository.findAll();
    }

    public Resena actualizarComentario(Integer resenaId, String nuevoComentario) {
        return resenaRepository.findById(resenaId)
                .map(resena -> {
                    resena.setComentario(nuevoComentario);
                    return resenaRepository.save(resena);
                })
                .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada con ID: " + resenaId));
    }

    public Resena actualizarCalificacion(Integer resenaId, Integer nuevaCalificacion) {
        if (nuevaCalificacion < 1 || nuevaCalificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }

        return resenaRepository.findById(resenaId)
                .map(resena -> {
                    resena.setCalificacion(nuevaCalificacion);
                    return resenaRepository.save(resena);
                })
                .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada con ID: " + resenaId));
    }

    private void validarCalificacion(Integer calificacion) {
        if (calificacion == null || calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
    }

    public boolean esResenaDeCliente(Integer resenaId, Integer clienteId) {
        return resenaRepository.findById(resenaId)
                .map(resena -> resena.getCliente().getIdusuario().equals(clienteId))
                .orElse(false);
    }

}