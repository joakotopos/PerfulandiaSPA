package cl.duocuc.perfulandia.PerfulandiaSPA.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "inventario")
@Data
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idinventario;

    @Column(nullable = false) //no se como conectar este modelo a productos
    private String productos;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idproducto")
    private Productos producto;

    @Column(nullable = false)
    private int cantidad;



}
}