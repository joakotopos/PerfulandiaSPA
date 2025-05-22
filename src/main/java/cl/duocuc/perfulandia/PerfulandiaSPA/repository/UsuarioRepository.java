package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Usuario;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreousuario(String correo);
    
    List<Usuario> findBySucursal_Idsucursal(Integer sucursalId);
    
    @Query("SELECT u FROM Administrador u")
    List<Administrador> FindAllAdministradores();
}