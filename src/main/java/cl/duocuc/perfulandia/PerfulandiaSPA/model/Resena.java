package cl.duocuc.perfulandia.PerfulandiaSPA.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "resena")
@Getter
@Setter
@NoArgsConstructor
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idresena;
    
    @Column(length = 1000)
    private String comentario;

    @Column(nullable = false)
    private Integer calificacion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcliente",nullable = false)
    @ToString.Exclude
    private Cliente cliente;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idproducto",nullable = false)
    @ToString.Exclude
    private Productos producto;
}