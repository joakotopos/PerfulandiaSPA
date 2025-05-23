package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Carrito;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.Pedido;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.CarritoRepository;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final CarritoRepository carritoRepository;
    private final ProductosService productoService;

    public Pedido crearPedido(Integer clienteId) {
        List<Carrito> itemsCarrito = carritoRepository.findByClienteId(clienteId);
        if (itemsCarrito.isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        List<Pedido> pedidosCreados = new ArrayList<>();
        
        try {
            for (Carrito item : itemsCarrito) {
                Pedido pedido = new Pedido();
                pedido.setCliente(item.getCliente());
                pedido.setProductos(item.getProducto());
                pedido.setEstado("PENDIENTE");
                

                BigDecimal precio = item.getProducto().getPrecio()
                        .multiply(new BigDecimal(item.getCantidad()));
                pedido.setPrecio(precio);
                

                productoService.actualizarStock(
                    item.getProducto().getIdproducto(), 
                    1, // operación de descuento
                    item.getCantidad()
                );
                
                pedidosCreados.add(pedidoRepository.save(pedido));
                

                carritoRepository.eliminarItem(clienteId, item.getProducto().getIdproducto());
            }
            
            return pedidosCreados.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear los pedidos: " + e.getMessage());
        }
    }

    public List<Pedido> listarPedidosPorCliente(Integer clienteId) {
        return pedidoRepository.findByCliente_Idcliente(clienteId);
    }
    
    public List<Pedido> buscarPedidosPorEstado(String estado) {
        return pedidoRepository.findByEstado(estado);
    }
    
    public List<Pedido> buscarPedidosPorRangoPrecio(BigDecimal precioMinimo, BigDecimal precioMaximo) {
        return pedidoRepository.findByPrecioRange(precioMinimo, precioMaximo);
    }
}