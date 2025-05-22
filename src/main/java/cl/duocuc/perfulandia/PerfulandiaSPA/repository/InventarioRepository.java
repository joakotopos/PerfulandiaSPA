package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    @Query("SELECT i FROM Inventario i WHERE i.sucursal.idsucursal = :sucursalId AND i.cantidad > 0")
    List<Inventario> findBySucursalesconStock(@Param("productoId") Integer productoId);

    @Query("SELECT i FROM Inventario i WHERE i.sucursal.idsucursal = :sucursalId AND i.cantidad <= 0")
    List<Inventario> findBySucursalesSinStock(@Param("sucursalId") Integer sucursalId);

    @Modifying
    @Query("UPDATE Inventario i SET i.cantidad = i.cantidad - :cantidad WHERE i.producto.idproducto = :productoId AND i.sucursal.idsucursal = :sucursalId")
    void descontarStock(@Param("sucursalId") Integer sucursalId, @Param("productoId") Integer productoId, @Param("cantidad") int cantidad);
}
