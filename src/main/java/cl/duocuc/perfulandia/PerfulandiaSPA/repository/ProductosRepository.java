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

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {
    List<Productos> findByCategoria(String categoria);
    
    @Query("SELECT p FROM Productos p WHERE p.precio BETWEEN :min AND :max")
    List<Productos> findByRangoPrecio(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    @Modifying
    @Transactional
    @Query("UPDATE Productos p SET p.stock = p.stock - :cantidad WHERE p.idproducto = :id")
    void reducirStock(@Param("id") Integer id, @Param("cantidad") int cantidad);
}