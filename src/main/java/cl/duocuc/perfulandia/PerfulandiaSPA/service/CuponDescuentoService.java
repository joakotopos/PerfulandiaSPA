package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.CuponDescuento;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.CuponDescuentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CuponDescuentoService {

    private final CuponDescuentoRepository cuponDescuentoRepository;

    public CuponDescuento crearCuponDescuento(String codigo, BigDecimal descuento, LocalDate fechaExpiracion) {
        if (descuento.compareTo(BigDecimal.ZERO) <= 0 || descuento.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }

        if (fechaExpiracion.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de expiración no puede ser anterior a la fecha actual");
        }

        Optional<CuponDescuento> cuponExistente = cuponDescuentoRepository.findById(codigo);
        if (cuponExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un cupón con el código: " + codigo);
        }

        CuponDescuento cupon = new CuponDescuento();
        cupon.setCodigo(codigo);
        cupon.setDescuento(descuento);
        cupon.setFechaexpiracion(fechaExpiracion);
        cupon.setActivo(true);
        
        return cuponDescuentoRepository.save(cupon);
    }

    public List<CuponDescuento> obtenerCuponesValidos() {
        return cuponDescuentoRepository.findByCuponesValidos();
    }

    public Optional<CuponDescuento> obtenerCuponPorPedido(Integer pedidoId) {
        return cuponDescuentoRepository.findByPedidoId(pedidoId);
    }

    public void desactivarCupon(String codigo) {
        Optional<CuponDescuento> cuponOpt = cuponDescuentoRepository.findById(codigo);
        if (cuponOpt.isEmpty()) {
            throw new IllegalArgumentException("Cupón no encontrado: " + codigo);
        }
        cuponDescuentoRepository.actualizarEstadoCupon(codigo, false);
    }

    public void activarCupon(String codigo) {
        Optional<CuponDescuento> cuponOpt = cuponDescuentoRepository.findById(codigo);
        if (cuponOpt.isEmpty()) {
            throw new IllegalArgumentException("Cupón no encontrado: " + codigo);
        }
        cuponDescuentoRepository.actualizarEstadoCupon(codigo, true);
    }

    public boolean validarCupon(String codigo) {
        Optional<CuponDescuento> cuponOpt = cuponDescuentoRepository.findById(codigo);
        if (cuponOpt.isEmpty()) {
            return false;
        }

        CuponDescuento cupon = cuponOpt.get();
        LocalDate hoy = LocalDate.now();
        return cupon.isActivo() && !cupon.getFechaexpiracion().isBefore(hoy);
    }

    public CuponDescuento actualizarFechaExpiracion(String codigo, LocalDate nuevaFecha) {
        if (nuevaFecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de expiración no puede ser anterior a la fecha actual");
        }

        return cuponDescuentoRepository.findById(codigo)
                .map(cupon -> {
                    cupon.setFechaexpiracion(nuevaFecha);
                    return cuponDescuentoRepository.save(cupon);
                })
                .orElseThrow(() -> new IllegalArgumentException("Cupón no encontrado: " + codigo));
    }

    public CuponDescuento actualizarDescuento(String codigo, BigDecimal nuevoDescuento) {
        if (nuevoDescuento.compareTo(BigDecimal.ZERO) <= 0 || nuevoDescuento.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }

        return cuponDescuentoRepository.findById(codigo)
                .map(cupon -> {
                    cupon.setDescuento(nuevoDescuento);
                    return cuponDescuentoRepository.save(cupon);
                })
                .orElseThrow(() -> new IllegalArgumentException("Cupón no encontrado: " + codigo));
    }

    public List<CuponDescuento> obtenerTodosLosCupones() {
        return cuponDescuentoRepository.findAll();
    }

    public boolean existeCupon(String codigo) {
        return cuponDescuentoRepository.findById(codigo).isPresent();
    }

    public void eliminarCupon(String codigo) {
        Optional<CuponDescuento> cuponOpt = cuponDescuentoRepository.findById(codigo);
        if (cuponOpt.isEmpty()) {
            throw new IllegalArgumentException("Cupón no encontrado: " + codigo);
        }
        cuponDescuentoRepository.deleteById(codigo);
    }
}