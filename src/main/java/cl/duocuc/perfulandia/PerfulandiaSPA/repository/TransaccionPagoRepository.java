package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.TransaccionPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransaccionPagoRepository extends JpaRepository<TransaccionPago, Integer> {

    @Query("SELECT t FROM TransaccionPago t WHERE t.metodopago = :metodo")
    List<TransaccionPago> findByMetodoPago(@Param("metodo") TransaccionPago.MetodoPago metodo);

    @Query("SELECT t FROM TransaccionPago t WHERE t.pedido.idPedido = :pedidoId")
    Optional<TransaccionPago> findByPedidoId(@Param("pedidoId") Integer pedidoId);

    @Query("SELECT SUM(t.monto) FROM TransaccionPago t WHERE t.fecha BETWEEN :inicio AND :fin")
    BigDecimal sumVentasPorPeriodo(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);
}
