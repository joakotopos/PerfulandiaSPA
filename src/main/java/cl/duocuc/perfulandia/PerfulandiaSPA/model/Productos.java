package cl.duocuc.perfulandia.PerfulandiaSPA.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Data
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproducto;
    
    @Column(nullable = false)
    private String nombreproducto;
    
    @Column(length = 1000)
    private String descripcionproducto;
    
    @Column(nullable = false)
    private BigDecimal precioproducto;
    
    @Column(nullable = false)
    private int stockproducto;
    
    @Column(length = 500)
    private String resenaproducto;
}