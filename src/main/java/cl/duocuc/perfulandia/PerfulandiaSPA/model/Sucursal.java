package cl.duocuc.perfulandia.PerfulandiaSPA.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sucursal")
@Getter
@Setter
@NoArgsConstructor
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idsucursal;

    @Column(length = 255,nullable = false)
    private String nombre;

    @Column(length = 255,nullable = false)
    private String direccion;

    @Column(length = 255,nullable = false)
    private String ciudad;

    @Column(length = 255,nullable = false)
    private String horarioapertura;

    @Column(length = 255,nullable = false)
    private String horariocierre;

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Inventario> inventarios;

    @OneToMany(mappedBy = "sucursal")
    @ToString.Exclude
    private List<Usuario> empleados;
}
