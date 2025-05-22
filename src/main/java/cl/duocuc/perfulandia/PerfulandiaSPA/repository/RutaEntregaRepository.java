package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.RutaEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RutaEntregaRepository extends JpaRepository<RutaEntrega, String> {

    @Query("SELECT r FROM RutaEntrega r WHERE r.estado = :estado")
    List<RutaEntrega> findByEstado(@Param("estado") String estado);

    @Query("SELECT r FROM RutaEntrega r WHERE r.pedido.cliente.idusuario = :clienteId")
    List<RutaEntrega> findByClienteId(@Param("clienteId") Integer clienteId);

    @Query("SELECT r FROM RutaEntrega r WHERE r.fecha = :fecha ORDER BY r.pedido.cliente.direccionenvio")
    List<RutaEntrega> findRutasAndFecha(@Param("fecha") LocalDate fecha);
}
