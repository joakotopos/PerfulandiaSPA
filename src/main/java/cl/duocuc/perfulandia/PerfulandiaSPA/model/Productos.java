package cl.duocuc.perfulandia.PerfulandiaSPA.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproducto;
    
    @Column(length = 100,nullable = false)
    private String nombre;
    
    @Column(length = 1000)
    private String descripcion;
    
    @Column(nullable = false,precision = 10, scale = 2 )
    private BigDecimal precio;
    
    @Column(nullable = false)
    private int stock;
    
    @Column(length = 500)
    private String categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproveedor")
    @ToString.Exclude
    private Proveedor proveedor;
}