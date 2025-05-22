package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    @Query("SELECT s FROM Sucursal s WHERE s.ciudad = :ciudad")
    List<Sucursal> findByCiudad(@Param("ciudad") String ciudad);

    @Query("SELECT s FROM Sucursal s JOIN s.inventarios i WHERE i.producto.idproducto = :productoId AND i.cantidad > 0")
    List<Sucursal> findSucursalesConProducto(@Param("productoId") Integer productoId);

    @Query("SELECT s FROM Sucursal s WHERE :hora BETWEEN s.horarioapertura AND s.horariocierre")
    List<Sucursal> findSucursalesAbiertas(@Param("hora") String horaActual);
}
