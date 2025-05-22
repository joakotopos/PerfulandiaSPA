package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    @Query("SELECT p FROM Proveedor p WHERE SIZE(p.productos) > 0")
    List<Proveedor> findProveedoresConProductos();

    @Query("SELECT p FROM Proveedor p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Proveedor> findByNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Proveedor p JOIN p.productos prod WHERE prod.idproducto = :productoId")
    Optional<Proveedor> findByProductoId(@Param("productoId") Integer productoId);

}
