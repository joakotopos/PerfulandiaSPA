package cl.duocuc.perfulandia.PerfulandiaSPA.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cupondescuento")
@Getter
@Setter
@NoArgsConstructor
public class CuponDescuento {
    @Id
    @Column(length = 50)
    private String codigo;

    @Column(nullable = false,precision = 5,scale = 2)
    private BigDecimal descuento;

    @Column(nullable = false)
    private LocalDate fechaexpiracion;

    @Column(nullable = false,columnDefinition = "boolean default true")
    private boolean activo = true;

    @ManyToMany
    @JoinTable(
            name = "pedido_cupon",
            joinColumns = @JoinColumn(name = "codigo_cupon"),
            inverseJoinColumns = @JoinColumn(name = "id_pedido")
    )
    @ToString.Exclude
    private List<Pedido> pedidos;


}
