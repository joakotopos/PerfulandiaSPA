package cl.duocuc.perfulandia.PerfulandiaSPA.controller;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Factura;
import cl.duocuc.perfulandia.PerfulandiaSPA.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    private final FacturaService facturaService;

    @Autowired
    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura) {
        try {
            Factura nuevaFactura = facturaService.crearFactura(factura);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFactura);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFactura(@PathVariable Integer id) {
        try {
            Factura factura = facturaService.obtenerFacturaPorId(id);
            return factura != null ? ResponseEntity.ok(factura) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Factura>> obtenerTodasFacturas() {
        List<Factura> facturas = facturaService.obtenerTodasLasFacturas();
        return facturas.isEmpty() 
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(facturas);
    }

    @GetMapping("/mes")
    public ResponseEntity<List<Factura>> obtenerFacturasPorMes(
            @RequestParam int mes,
            @RequestParam int ano) {
        try {
            List<Factura> facturas = facturaService.obtenerFacturasPorMesYAno(mes, ano);
            return facturas.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(facturas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Factura>> obtenerHistorialCliente(@PathVariable Integer clienteId) {
        try {
            List<Factura> historial = facturaService.obtenerHistorialCliente(clienteId);
            return historial.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(historial);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hoy")
    public ResponseEntity<List<Factura>> obtenerFacturasHoy() {
        List<Factura> facturasHoy = facturaService.obtenerFacturasDelDia();
        return facturasHoy.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(facturasHoy);
    }

    @GetMapping("/total-mensual")
    public ResponseEntity<BigDecimal> calcularTotalMensual(
            @RequestParam int mes,
            @RequestParam int ano) {
        try {
            BigDecimal total = facturaService.calcularMontoTotalMensual(mes, ano);
            return total != null ? ResponseEntity.ok(total) : ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Integer id) {
        try {
            facturaService.eliminarFactura(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}