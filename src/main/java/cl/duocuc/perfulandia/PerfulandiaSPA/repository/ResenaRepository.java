package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResenaRepository extends JpaRepository<Resena, Integer> {

    @Query("SELECT r FROM Resena r WHERE r.producto.idproducto = :productoId ORDER BY r.calificacion DESC")
    List<Resena> findByProductoOrdenado(@Param("productoId") Integer productoId);

    @Query("SELECT COALESCE(AVG(r.calificacion), 0.0) FROM Resena r WHERE r.producto.idproducto = :productoId")
    Double getAverageCalificacionProducto(@Param("productoId") Integer productoId);

    @Query("SELECT r FROM Resena r WHERE r.cliente.idusuario = :clienteId AND r.producto.idproducto = :productoId")
    Optional<Resena> findResenaExistente(
        @Param("clienteId") Integer clienteId,
        @Param("productoId") Integer productoId
    );
    
    @Query("SELECT COUNT(r) > 0 FROM Resena r WHERE r.cliente.idusuario = :clienteId AND r.producto.idproducto = :productoId")
    boolean existeResena(
        @Param("clienteId") Integer clienteId,
        @Param("productoId") Integer productoId
    );
}