package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    @Query("SELECT c FROM  Carrito c WHERE c.cliente.idusuario = :clienteId")
    List<Carrito> findByClienteId(@Param("clienteId") Integer clienteId);

    @Query("SELECT SUM(c.cantidad * p.precio) FROM Carrito c JOIN c.producto p WHERE c.cliente.idusuario = :clienteId")
    BigDecimal calcularTotalCarrito(@Param("clienteId") Integer clienteId);

    @Modifying
    @Query("DELETE FROM Carrito c WHERE c.cliente.idusuario = :clienteId AND c.producto.idproducto = :productoId")
    void eliminarItem(@Param("clienteId") Integer clienteId, @Param("productoId") Integer productoId);

    @Modifying
    @Query("DELETE FROM Carrito c WHERE c.cliente.idusuario = :clienteId")
    void vaciarCarrito(@Param("clienteId") Integer clienteId);
}