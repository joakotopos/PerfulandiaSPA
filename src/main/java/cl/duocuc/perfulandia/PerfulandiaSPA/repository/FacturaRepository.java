package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    
    @Query("SELECT f FROM Factura f WHERE YEAR(f.fechaemision) = :year AND MONTH(f.fechaemision) = :month")
    List<Factura> findByMesAndAno(@Param("month") int month, @Param("year") int year);
    
    @Query("SELECT DISTINCT f FROM Factura f " +
           "LEFT JOIN FETCH f.pedido p " +
           "LEFT JOIN FETCH f.cliente c " +
           "WHERE f.cliente.idusuario = :clienteId " +
           "ORDER BY f.fechaemision DESC")
    List<Factura> findHistorialCliente(@Param("clienteId") Integer clienteId);
    
    @Query("SELECT COUNT(f) FROM Factura f WHERE f.cliente.idusuario = :clienteId")
    Long countFacturasByCliente(@Param("clienteId") Integer clienteId);
    
    @Query("SELECT f FROM Factura f WHERE f.fechaemision = CURRENT_DATE")
    List<Factura> findFacturasHoy();
    
    @Query("SELECT SUM(f.monto) FROM Factura f WHERE YEAR(f.fechaemision) = :year AND MONTH(f.fechaemision) = :month")
    BigDecimal calcularMontoTotalPorMes(@Param("month") int month, @Param("year") int year);
}