package cl.duocuc.perfulandia.PerfulandiaSPA.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cliente") // lol lmao even
@Data
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Usuario {
    
    @Column(length = 255)
    private String direccionenvio;
    
    @Column(length = 255)
    private String historialpedidos;
}