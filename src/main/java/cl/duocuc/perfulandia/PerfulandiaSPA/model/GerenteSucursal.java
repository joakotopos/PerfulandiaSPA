package cl.duocuc.perfulandia.PerfulandiaSPA.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gerentesucursal")
@Getter
@Setter
@NoArgsConstructor
public class GerenteSucursal extends Usuario {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsucursal")
    private Sucursal sucursal;

}
