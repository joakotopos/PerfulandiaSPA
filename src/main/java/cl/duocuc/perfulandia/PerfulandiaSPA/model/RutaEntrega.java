package cl.duocuc.perfulandia.PerfulandiaSPA.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rutaentrega")
@Getter
@Setter
@NoArgsConstructor

public class RutaEntrega {
    @Id
    @Column(length = 50,nullable = false)
    private String idtransaccion;

    @Column(nullable = false,precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false,length = 30)
    private String estado;

    @Column(nullable = false)
    private LocalDate fecha;

    @OneToOne
    @JoinColumn(name = "idpedido",nullable = false)
    private Pedido pedido;

}
