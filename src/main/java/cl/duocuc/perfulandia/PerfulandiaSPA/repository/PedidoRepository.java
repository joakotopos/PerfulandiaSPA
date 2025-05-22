package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente_Idcliente(Integer clienteId);
    
    List<Pedido> findByEstado(String estado);


    @Query("SELECT p FROM Pedido p WHERE p.precio BETWEEN :precioMinimo AND :precioMaximo")
    List<Pedido> findByPrecioRange(@Param("precioMinimo") BigDecimal precioMinimo, @Param("precioMaximo") BigDecimal precioMaximo);
}