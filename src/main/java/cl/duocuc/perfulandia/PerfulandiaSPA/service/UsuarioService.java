package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Usuario;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Usuario usuario) {
        validarUsuario(usuario);
        if (usuarioRepository.findByCorreousuario(usuario.getCorreousuario()).isPresent()) {
            throw new IllegalStateException("El correo ya está registrado");
        }
        return usuarioRepository.save(usuario);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        if (usuario.getCorreousuario() == null || usuario.getCorreousuario().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        if (!validarFormatoCorreo(usuario.getCorreousuario())) {
            throw new IllegalArgumentException("El formato del correo no es válido");
        }
        if (usuario.getNombreusuario() == null || usuario.getNombreusuario().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (usuario.getContrasenausuario() == null || usuario.getContrasenausuario().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
    }

    private boolean validarFormatoCorreo(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(regex).matcher(correo).matches();
    }

    public Optional<Usuario> findByCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio para la búsqueda");
        }
        return usuarioRepository.findByCorreousuario(correo);
    }

    public List<Usuario> listarUsuariosPorSucursal(Integer sucursalId) {
        if (sucursalId == null) {
            throw new IllegalArgumentException("El ID de la sucursal es obligatorio");
        }
        return usuarioRepository.findBySucursal_Idsucursal(sucursalId);
    }
}