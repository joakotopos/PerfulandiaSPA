package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Inventario;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Productos;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Sucursal;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.InventarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public List<Inventario> obtenerProductosConStock(Integer sucursalId) {
        return inventarioRepository.findBySucursalesconStock(sucursalId);
    }

    public List<Inventario> obtenerProductosSinStock(Integer sucursalId) {
        return inventarioRepository.findBySucursalesSinStock(sucursalId);
    }

    @Transactional
    public void descontarStock(Integer sucursalId, Integer productoId, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a descontar debe ser mayor que 0");
        }

        Optional<Inventario> inventario = inventarioRepository.findById(productoId);
        if (inventario.isPresent() && inventario.get().getCantidad() >= cantidad) {
            inventarioRepository.descontarStock(sucursalId, productoId, cantidad);
        } else {
            throw new IllegalStateException("No hay suficiente stock disponible");
        }
    }

    public Inventario crearInventario(Productos producto, Sucursal sucursal, int cantidad) {
        Inventario inventario = new Inventario();
        inventario.setProducto(producto);
        inventario.setSucursal(sucursal);
        inventario.setCantidad(cantidad);
        return inventarioRepository.save(inventario);
    }

    public Inventario actualizarCantidad(Integer inventarioId, int nuevaCantidad) {
        Optional<Inventario> inventario = inventarioRepository.findById(inventarioId);
        if (inventario.isPresent()) {
            Inventario inv = inventario.get();
            inv.setCantidad(nuevaCantidad);
            return inventarioRepository.save(inv);
        }
        throw new IllegalArgumentException("Inventario no encontrado");
    }

    public boolean hayStockSuficiente(Integer sucursalId, Integer productoId, int cantidadRequerida) {
        Optional<Inventario> inventario = inventarioRepository.findById(productoId);
        return inventario.map(inv -> inv.getCantidad() >= cantidadRequerida).orElse(false);
    }

    public List<Inventario> obtenerTodoInventario() {
        return inventarioRepository.findAll();
    }

    public void eliminarInventario(Integer inventarioId) {
        inventarioRepository.deleteById(inventarioId);
    }

    public Optional<Inventario> obtenerInventarioPorId(Integer inventarioId) {
        return inventarioRepository.findById(inventarioId);
    }

    @Transactional
    public void agregarStock(Integer inventarioId, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a agregar debe ser mayor que 0");
        }

        Optional<Inventario> inventario = inventarioRepository.findById(inventarioId);
        if (inventario.isPresent()) {
            Inventario inv = inventario.get();
            inv.setCantidad(inv.getCantidad() + cantidad);
            inventarioRepository.save(inv);
        } else {
            throw new IllegalArgumentException("Inventario no encontrado");
        }
    }

}