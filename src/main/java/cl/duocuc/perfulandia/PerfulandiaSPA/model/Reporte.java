package cl.duocuc.perfulandia.PerfulandiaSPA.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Table(name = "reporte")
@Getter
@Setter
@NoArgsConstructor
public class Reporte {
    public enum TipoReporte {
        Ventas,
        Inventario,
        Clientes
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idreporte;

    @Column(nullable = false,length = 50)
    @Enumerated(EnumType.STRING)
    private TipoReporte tipo;

    @Column(nullable = false,updatable = false)
    private LocalDate fechageneracion = LocalDate.now();

    @Column(length = 1000)
    private String observacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsucursal")
    @ToString.Exclude
    private Sucursal sucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario")
    @ToString.Exclude
    private Usuario Generadopor;


}
