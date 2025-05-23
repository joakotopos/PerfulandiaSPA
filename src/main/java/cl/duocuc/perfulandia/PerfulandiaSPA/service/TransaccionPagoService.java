package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Pedido;
import cl.duocuc.perfulandia.PerfulandiaSPA.model.TransaccionPago;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.TransaccionPagoRepository;
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


public class TransaccionPagoService {

    private final TransaccionPagoRepository transaccionPagoRepository;

    public TransaccionPago crearTransaccion(Pedido pedido, BigDecimal monto, TransaccionPago.MetodoPago metodoPago) {
        validarMonto(monto);

        TransaccionPago transaccion = new TransaccionPago();
        transaccion.setIdtransaccion(generarIdTransaccion());
        transaccion.setPedido(pedido);
        transaccion.setMonto(monto);
        transaccion.setMetodopago(metodoPago);
        transaccion.setEstado("PENDIENTE");
        transaccion.setFecha(LocalDate.now());

        return transaccionPagoRepository.save(transaccion);
    }

    private String generarIdTransaccion() {
        return "TRANS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<TransaccionPago> obtenerTransaccionesPorMetodoPago(TransaccionPago.MetodoPago metodo) {
        return transaccionPagoRepository.findByMetodoPago(metodo);
    }

    public Optional<TransaccionPago> obtenerTransaccionPorPedido(Integer pedidoId) {
        return transaccionPagoRepository.findByPedidoId(pedidoId);
    }

    public BigDecimal calcularVentasPorPeriodo(LocalDate inicio, LocalDate fin) {
        if (inicio.isAfter(fin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha final");
        }
        BigDecimal resultado = transaccionPagoRepository.sumVentasPorPeriodo(inicio, fin);
        return resultado != null ? resultado : BigDecimal.ZERO;
    }

    public TransaccionPago actualizarEstado(String idTransaccion, String nuevoEstado) {
        return transaccionPagoRepository.findById(Integer.valueOf(idTransaccion))
                .map(transaccion -> {
                    validarEstado(nuevoEstado);
                    transaccion.setEstado(nuevoEstado.toUpperCase());
                    return transaccionPagoRepository.save(transaccion);
                })
                .orElseThrow(() -> new IllegalArgumentException("Transacción no encontrada: " + idTransaccion));
    }

    private void validarEstado(String estado) {
        List<String> estadosValidos = List.of("PENDIENTE", "PROCESANDO", "COMPLETADO", "FALLIDO", "CANCELADO");
        if (!estadosValidos.contains(estado.toUpperCase())) {
            throw new IllegalArgumentException("Estado no válido. Estados permitidos: " + estadosValidos);
        }
    }

    private void validarMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
    }

    public TransaccionPago procesarPago(String idTransaccion) {
        return transaccionPagoRepository.findById(Integer.valueOf(idTransaccion))
                .map(transaccion -> {
                    // Aquí se podría agregar lógica adicional de procesamiento de pago
                    transaccion.setEstado("COMPLETADO");
                    return transaccionPagoRepository.save(transaccion);
                })
                .orElseThrow(() -> new IllegalArgumentException("Transacción no encontrada: " + idTransaccion));
    }

    public TransaccionPago cancelarTransaccion(String idTransaccion) {
        return actualizarEstado(idTransaccion, "CANCELADO");
    }

    public List<TransaccionPago> obtenerTodasLasTransacciones() {
        return transaccionPagoRepository.findAll();
    }

    public boolean existeTransaccion(String idTransaccion) {
        return transaccionPagoRepository.existsById(Integer.valueOf(idTransaccion));
    }

    public BigDecimal obtenerVentasDelDia() {
        LocalDate hoy = LocalDate.now();
        return calcularVentasPorPeriodo(hoy, hoy);
    }

    public BigDecimal obtenerVentasDelMes() {
        LocalDate inicio = LocalDate.now().withDayOfMonth(1);
        LocalDate fin = LocalDate.now();
        return calcularVentasPorPeriodo(inicio, fin);
    }

}