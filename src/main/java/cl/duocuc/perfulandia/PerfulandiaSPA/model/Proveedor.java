package cl.duocuc.perfulandia.PerfulandiaSPA.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "proveedor")
@Getter
@Setter
@NoArgsConstructor
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproveedor;

    @Column(length = 255,nullable = false)
    private String nombre;

    @Column(length = 255)
    private String contacto;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Productos> productos;
}
