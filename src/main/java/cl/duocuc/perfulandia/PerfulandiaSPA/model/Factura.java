package cl.duocuc.perfulandia.PerfulandiaSPA.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "factura")
@Getter
@Setter
@NoArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idfactura;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idpedido",nullable = false)
    @ToString.Exclude
    private Pedido pedido;

    @Column(nullable = false,precision = 10,scale = 2 )
    private BigDecimal monto;

    @Column(nullable = false,updatable = false)
    private LocalDate fechaemision = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcliente",nullable = false)
    @ToString.Exclude
    private Cliente cliente;

}
