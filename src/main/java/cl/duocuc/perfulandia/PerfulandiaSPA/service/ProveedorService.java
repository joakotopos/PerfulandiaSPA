package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Proveedor;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.ProveedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class ProveedorService {
    private final ProveedorRepository proveedorRepository;

    public Proveedor crearProveedor(Proveedor proveedor) {
        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proveedor es obligatorio");
        }
        return proveedorRepository.save(proveedor);
    }

    public List<Proveedor> obtenerTodosProveedores() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> obtenerProveedorPorId(Integer id) {
        return proveedorRepository.findById(id);
    }

    public List<Proveedor> obtenerProveedoresConProductos() {
        return proveedorRepository.findProveedoresConProductos();
    }

    public List<Proveedor> buscarProveedoresPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de búsqueda no puede estar vacío");
        }
        return proveedorRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }

    public Optional<Proveedor> encontrarProveedorPorProducto(Integer productoId) {
        return proveedorRepository.findByProductoId(productoId);
    }

    public Proveedor actualizarProveedor(Integer id, Proveedor proveedorActualizado) {
        return proveedorRepository.findById(id)
                .map(proveedor -> {
                    proveedor.setNombre(proveedorActualizado.getNombre());
                    proveedor.setContacto(proveedorActualizado.getContacto());
                    return proveedorRepository.save(proveedor);
                })
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado con ID: " + id));
    }

    public void eliminarProveedor(Integer id) {
        if (proveedorRepository.tieneProductosAsociados(id)) {
            throw new IllegalStateException("No se puede eliminar el proveedor porque tiene productos asociados");
        }
        proveedorRepository.deleteById(id);
    }

    public boolean tieneProductosAsociados(Integer id) {
        return proveedorRepository.tieneProductosAsociados(id);
    }

    public Proveedor actualizarContactoProveedor(Integer id, String nuevoContacto) {
        return proveedorRepository.findById(id)
                .map(proveedor -> {
                    proveedor.setContacto(nuevoContacto);
                    return proveedorRepository.save(proveedor);
                })
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado con ID: " + id));
    }

    public boolean existeProveedor(Integer id) {
        return proveedorRepository.existsById(id);
    }

}