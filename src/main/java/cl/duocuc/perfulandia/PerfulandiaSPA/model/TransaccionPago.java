package cl.duocuc.perfulandia.PerfulandiaSPA.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaccionpago")
@Getter
@Setter
@NoArgsConstructor
public class TransaccionPago {
    public enum MetodoPago {
        Tarjeta_credito,
        Tarjeta_debito,
        Efectivo
    }
    @Id
    @Column(length = 50,nullable = false)
    private String idtransaccion;

    @Column(nullable = false,precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false,length = 20)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodopago;

    @Column(nullable = false,length = 20)
    private String estado;

    @Column(nullable = false)
    private LocalDate fecha;

    @OneToOne
    @JoinColumn(name = "idpedido",nullable = false)
    private Pedido pedido;
}
