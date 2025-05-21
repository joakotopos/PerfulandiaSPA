package cl.duocuc.perfulandia.PerfulandiaSPA.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true,exclude = {"pedidos","carrito"})
public class Cliente extends Usuario {
    
    @Column(length = 255)
    private String direccionenvio;

    @OneToMany(mappedBy = "cliente")
    @ToString.Exclude
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "cliente")
    @ToString.Exclude
    private List<Carrito> carrito;
}