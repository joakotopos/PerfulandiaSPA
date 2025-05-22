package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Productos;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.InventarioRepository;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.ProductosRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductosService {
    private final ProductosRepository productosRepository;
    private final InventarioRepository inventarioRepository; // Corregido el nombre

    public Productos crearProducto(Productos producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        validarProducto(producto);
        return productosRepository.save(producto);
    }

    private void validarProducto(Productos producto) {
        if (producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock debe ser mayor que cero");
        }
    }

    public List<Productos> buscarPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede estar vacía");
        }
        return productosRepository.findByCategoria(categoria);
    }

    public void actualizarStock(Integer productoId, Integer sucursalId, int cantidad) {
        if (productoId == null || sucursalId == null) {
            throw new IllegalArgumentException("Los IDs no pueden ser nulos");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        inventarioRepository.descontarStock(sucursalId, productoId, cantidad);
    }
}