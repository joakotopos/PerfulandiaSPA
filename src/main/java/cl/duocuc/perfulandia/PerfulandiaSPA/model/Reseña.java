package cl.duocuc.perfulandia.PerfulandiaSPA.model;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "Reseña")
@Data
public class Reseña {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer idreseña;

 @ManyToOne(fetch = FetchType.EAGER)
 @JoinColumn(name = "idusuario")
 private Productos producto;

 @Column(length = 255)
 private String historialpedidos;


}
