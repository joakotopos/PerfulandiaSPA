package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Carrito;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Cliente;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Productos;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.CarritoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional


public class CarritoService {
    private final CarritoRepository carritoRepository;

    public List<Carrito> obtenerCarritoPorCliente(Integer clienteId) {
        return carritoRepository.findByClienteId(clienteId);
    }

    public Carrito agregarAlCarrito(Cliente cliente, Productos producto, int cantidad) {
        // Verificar si ya existe el producto en el carrito
        List<Carrito> carritoExistente = carritoRepository.findByClienteId(cliente.getIdusuario());
        Optional<Carrito> itemExistente = carritoExistente.stream()
                .filter(item -> item.getProducto().getIdproducto().equals(producto.getIdproducto()))
                .findFirst();

        if (itemExistente.isPresent()) {

            Carrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
            return carritoRepository.save(item);
        } else {

            Carrito nuevoItem = new Carrito();
            nuevoItem.setCliente(cliente);
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(cantidad);
            return carritoRepository.save(nuevoItem);
        }
    }

    public Carrito actualizarCantidad(Integer carritoId, int nuevaCantidad) {
        Optional<Carrito> item = carritoRepository.findById(carritoId);
        if (item.isPresent()) {
            Carrito carritoItem = item.get();
            carritoItem.setCantidad(nuevaCantidad);
            return carritoRepository.save(carritoItem);
        }
        return null;
    }

    public void eliminarItemCarrito(Integer clienteId, Integer productoId) {
        carritoRepository.eliminarItem(clienteId, productoId);
    }

    public BigDecimal calcularTotal(Integer clienteId) {
        BigDecimal total = carritoRepository.calcularTotalCarrito(clienteId);
        return total != null ? total : BigDecimal.ZERO;
    }

    public void vaciarCarrito(Integer clienteId) {
        List<Carrito> items = carritoRepository.findByClienteId(clienteId);
        carritoRepository.deleteAll(items);
    }

    public boolean carritoVacio(Integer clienteId) {
        List<Carrito> items = carritoRepository.findByClienteId(clienteId);
        return items.isEmpty();
    }

    public int obtenerCantidadTotal(Integer clienteId) {
        List<Carrito> items = carritoRepository.findByClienteId(clienteId);
        return items.stream()
                .mapToInt(Carrito::getCantidad)
                .sum();
    }

}