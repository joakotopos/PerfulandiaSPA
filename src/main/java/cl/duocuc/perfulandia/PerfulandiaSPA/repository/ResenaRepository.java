package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResenaRepository extends JpaRepository<Resena, Integer> {

    @Query("SELECT r FROM Resena r WHERE r.producto.idproducto = :productoId ORDER BY r.calificacion DESC")
    List<Resena> findByOrdenRsenaProducto(@Param("productoId") Integer productoId);

    @Query("SELECT AVG(r.calificacion) FROM Resena r WHERE r.producto.idproducto = :productoId")
    Double getAverageCalificacionProducto(@Param("productoId") Integer productoId);

    @Query("SELECT r FROM Resena r WHERE r.cliente.idusuario = :clienteId AND r.producto.idproducto = :productoId")
    Optional<Resena> findResenaExistente(@Param("clienteId") Integer clienteId,@Param("productoId") Integer productoId);
}
