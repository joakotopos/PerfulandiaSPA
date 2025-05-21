package cl.duocuc.perfulandia.PerfulandiaSPA.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpedido")
    private Integer idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcliente",nullable = false)
    @ToString.Exclude
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproducto",nullable = false)
    @ToString.Exclude
    private Productos productos;

    @Column(nullable = false,length = 50)
    private String estado;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @OneToOne(mappedBy = "pedido")
    @ToString.Exclude
    private RutaEntrega rutaEntrega;

    @OneToOne(mappedBy = "pedido")
    @ToString.Exclude
    private TransaccionPago transaccionPago;

}
