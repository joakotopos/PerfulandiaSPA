package cl.duocuc.perfulandia.PerfulandiaSPA.repository;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    @Query("SELECT f FROM Factura f WHERE YEAR(f.fechaemision) = :year AND MONTH(f.fechaemision) = :month ")
    List<Factura> findBymesandano(@Param("month") int month, @Param("year") int year);

    @Query("SELECT f FROM Factura f JOIN f.pedido p WHERE p.cliente.idusuario = :clienteId ORDER BY f.fechaemision DESC")
    List<Factura> findHistorialCliente(@Param("clienteId") Integer clienteId);

    @Query(value = "SELECT f FROM factura f WHERE f.idpedido IN (SELECT p.idpedido FROM pedido p WHERE p.idcliente = :clienteId)",nativeQuery = true)
    List<Factura> findCliente(@Param("clienteId") Integer clienteId);
}
