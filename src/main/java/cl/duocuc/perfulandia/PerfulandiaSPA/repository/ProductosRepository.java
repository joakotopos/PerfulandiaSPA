package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {
    
    @Query("SELECT p FROM Productos p WHERE LOWER(p.categoria) = LOWER(:categoria)")
    List<Productos> findByCategoria(@Param("categoria") String categoria);
    
    @Query("SELECT p FROM Productos p WHERE p.precio BETWEEN :min AND :max ORDER BY p.precio ASC")
    List<Productos> findByRangoPrecio(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    @Modifying
    @Transactional
    @Query("UPDATE Productos p SET p.stock = p.stock - :cantidad WHERE p.idproducto = :id AND p.stock >= :cantidad")
    int reducirStock(@Param("id") Integer id, @Param("cantidad") int cantidad);
    
    @Query("SELECT p FROM Productos p WHERE p.stock < :cantidadMinima")
    List<Productos> findProductosBajoStock(@Param("cantidadMinima") int cantidadMinima);
    
    @Query("SELECT p FROM Productos p WHERE p.stock > 0 ORDER BY p.precio ASC")
    List<Productos> findProductosDisponibles();
    
    @Query("SELECT p FROM Productos p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Productos> findByNombreContaining(@Param("nombre") String nombre);
    
    @Query("SELECT p FROM Productos p WHERE p.proveedor.idproveedor = :proveedorId")
    List<Productos> findByProveedor(@Param("proveedorId") Integer proveedorId);
}