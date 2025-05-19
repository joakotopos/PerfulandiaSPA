package cl.duocuc.perfulandia.PerfulandiaSPA.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuario;

    @Column(length = 100)
    private String nombreusuario;

    @Column(length = 100)
    private String correousuario;

    @Column(length = 100)
    private String contrasenausuario;
}