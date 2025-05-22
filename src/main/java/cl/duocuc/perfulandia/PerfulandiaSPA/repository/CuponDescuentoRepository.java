package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.CuponDescuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CuponDescuentoRepository extends JpaRepository<CuponDescuento, Integer> {

    @Query("SELECT c FROM CuponDescuento c WHERE c.activo = true AND c.fechaexpiracion >= CURRENT_DATE")
    List<CuponDescuento> findByCuponesValidos();

    @Query("SELECT c FROM CuponDescuento c JOIN c.pedidos p WHERE p.idPedido = :pedidoId")
    Optional<CuponDescuento> findByPedidoId(@Param("pedidoId") Integer pedidoId);

    @Modifying
    @Query("UPDATE CuponDescuento c SET c.activo = :estado WHERE c.codigo = :codigo")
    void actualizarEstadoCupon(@Param("codigo") String codigo, @Param("estado") boolean estado);
}
