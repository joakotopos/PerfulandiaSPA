package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Factura;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FacturaService {
    
    @Autowired
    private FacturaRepository facturaRepository;

    @Transactional
    public Factura crearFactura(Factura factura) {
        validarFactura(factura);
        return facturaRepository.save(factura);
    }

    public Factura obtenerFacturaPorId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return facturaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada con ID: " + id));
    }

    public List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    public List<Factura> obtenerFacturasPorMesYAno(int mes, int ano) {
        validarMesYAno(mes, ano);
        return facturaRepository.findByMesAndAno(mes, ano);
    }

    public List<Factura> obtenerHistorialCliente(Integer clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
        }
        return facturaRepository.findHistorialCliente(clienteId);
    }

    public Long contarFacturasCliente(Integer clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
        }
        return facturaRepository.countFacturasByCliente(clienteId);
    }

    public List<Factura> obtenerFacturasDelDia() {
        return facturaRepository.findFacturasHoy();
    }

    public BigDecimal calcularMontoTotalMensual(int mes, int ano) {
        validarMesYAno(mes, ano);
        return facturaRepository.calcularMontoTotalPorMes(mes, ano);
    }

    @Transactional
    public Factura actualizarFactura(Integer id, Factura facturaActualizada) {
        if (id == null || facturaActualizada == null) {
            throw new IllegalArgumentException("ID y factura actualizada no pueden ser nulos");
        }

        Factura facturaExistente = obtenerFacturaPorId(id);
        validarFactura(facturaActualizada);
        
        facturaExistente.setMonto(facturaActualizada.getMonto());
        facturaExistente.setFechaemision(facturaActualizada.getFechaemision());
        facturaExistente.setCliente(facturaActualizada.getCliente());
        
        return facturaRepository.save(facturaExistente);
    }

    @Transactional
    public void eliminarFactura(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        facturaRepository.deleteById(id);
    }

    private void validarFactura(Factura factura) {
        if (factura == null) {
            throw new IllegalArgumentException("La factura no puede ser nula");
        }
        if (factura.getMonto() == null || factura.getMonto().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto de la factura debe ser válido y no negativo");
        }
        if (factura.getFechaemision() == null) {
            throw new IllegalArgumentException("La fecha de emisión no puede ser nula");
        }
        if (factura.getCliente() == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
    }

    private void validarMesYAno(int mes, int ano) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }
        if (ano < 2000 || ano > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("El año especificado no es válido");
        }
    }
}