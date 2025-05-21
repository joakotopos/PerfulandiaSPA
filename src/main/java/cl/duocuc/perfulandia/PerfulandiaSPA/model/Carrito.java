package cl.duocuc.perfulandia.PerfulandiaSPA.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "carrito")
@Getter
@Setter
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcarrito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcliente",nullable = false)
    @ToString.Exclude
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproducto",nullable = false)
    @ToString.Exclude
    private Productos producto;

    @Column(nullable = false)
    private int cantidad;
}
