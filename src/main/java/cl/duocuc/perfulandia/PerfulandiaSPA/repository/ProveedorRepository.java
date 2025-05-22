package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    @Query("SELECT DISTINCT p FROM Proveedor p LEFT JOIN FETCH p.productos WHERE SIZE(p.productos) > 0")
    List<Proveedor> findProveedoresConProductos();

    @Query("SELECT p FROM Proveedor p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Proveedor> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);

    @Query("SELECT p FROM Proveedor p JOIN FETCH p.productos prod WHERE prod.idproducto = :productoId")
    Optional<Proveedor> findByProductoId(@Param("productoId") Integer productoId);
    
    @Query("SELECT COUNT(p) > 0 FROM Proveedor p WHERE p.idproveedor = :id AND SIZE(p.productos) > 0")
    boolean tieneProductosAsociados(@Param("id") Integer id);
}