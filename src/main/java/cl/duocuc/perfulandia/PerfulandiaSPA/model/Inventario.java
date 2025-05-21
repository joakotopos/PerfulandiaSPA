package cl.duocuc.perfulandia.PerfulandiaSPA.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventario")
@Getter
@Setter
@NoArgsConstructor
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idinventario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproducto",nullable = false)
    private Productos producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsucursal",nullable = false)
    private Sucursal sucursal;

    @Column(nullable = false)
    private int cantidad;

    public void setCantidad(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser menor que 0");
        }
        this.cantidad = cantidad;
    }
}