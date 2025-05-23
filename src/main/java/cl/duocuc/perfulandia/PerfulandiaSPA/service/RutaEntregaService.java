package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Pedido;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.RutaEntrega;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.RutaEntregaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional


public class RutaEntregaService {

    private final RutaEntregaRepository rutaEntregaRepository;

    public RutaEntrega crearRutaEntrega(Pedido pedido, BigDecimal monto, LocalDate fecha) {
        RutaEntrega rutaEntrega = new RutaEntrega();
        rutaEntrega.setIdtransaccion(generarIdTransaccion());
        rutaEntrega.setPedido(pedido);
        rutaEntrega.setMonto(monto);
        rutaEntrega.setFecha(fecha);
        rutaEntrega.setEstado("PENDIENTE"); // Estado inicial
        return rutaEntregaRepository.save(rutaEntrega);
    }

    private String generarIdTransaccion() {
        return "RUTA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<RutaEntrega> obtenerRutasPorEstado(String estado) {
        return rutaEntregaRepository.findByEstado(estado);
    }

    public List<RutaEntrega> obtenerRutasPorCliente(Integer clienteId) {
        return rutaEntregaRepository.findByClienteId(clienteId);
    }

    public List<RutaEntrega> obtenerRutasPorFecha(LocalDate fecha) {
        return rutaEntregaRepository.findRutasAndFecha(fecha);
    }

    public RutaEntrega actualizarEstado(String idTransaccion, String nuevoEstado) {
        return rutaEntregaRepository.findById(idTransaccion)
                .map(ruta -> {
                    validarEstado(nuevoEstado);
                    ruta.setEstado(nuevoEstado.toUpperCase());
                    return rutaEntregaRepository.save(ruta);
                })
                .orElseThrow(() -> new IllegalArgumentException("Ruta de entrega no encontrada: " + idTransaccion));
    }

    private void validarEstado(String estado) {
        List<String> estadosValidos = List.of("PENDIENTE", "EN_RUTA", "ENTREGADO", "CANCELADO");
        if (!estadosValidos.contains(estado.toUpperCase())) {
            throw new IllegalArgumentException("Estado no válido. Estados permitidos: " + estadosValidos);
        }
    }

    public Optional<RutaEntrega> obtenerRutaPorId(String idTransaccion) {
        return rutaEntregaRepository.findById(idTransaccion);
    }

    public RutaEntrega actualizarFechaEntrega(String idTransaccion, LocalDate nuevaFecha) {
        return rutaEntregaRepository.findById(idTransaccion)
                .map(ruta -> {
                    ruta.setFecha(nuevaFecha);
                    return rutaEntregaRepository.save(ruta);
                })
                .orElseThrow(() -> new IllegalArgumentException("Ruta de entrega no encontrada: " + idTransaccion));
    }

    public RutaEntrega actualizarMonto(String idTransaccion, BigDecimal nuevoMonto) {
        if (nuevoMonto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }

        return rutaEntregaRepository.findById(idTransaccion)
                .map(ruta -> {
                    ruta.setMonto(nuevoMonto);
                    return rutaEntregaRepository.save(ruta);
                })
                .orElseThrow(() -> new IllegalArgumentException("Ruta de entrega no encontrada: " + idTransaccion));
    }

    public RutaEntrega cancelarRutaEntrega(String idTransaccion) {
        return actualizarEstado(idTransaccion, "CANCELADO");
    }

    public RutaEntrega marcarComoEntregado(String idTransaccion) {
        return actualizarEstado(idTransaccion, "ENTREGADO");
    }

    public List<RutaEntrega> obtenerTodasLasRutas() {
        return rutaEntregaRepository.findAll();
    }

    public boolean existeRuta(String idTransaccion) {
        return rutaEntregaRepository.existsById(idTransaccion);
    }

}