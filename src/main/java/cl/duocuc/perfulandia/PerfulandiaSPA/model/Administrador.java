package cl.duocuc.perfulandia.PerfulandiaSPA.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Administrador")
@Getter
@Setter
@NoArgsConstructor
public class Administrador extends Usuario{
}
