package cl.duocuc.perfulandia.PerfulandiaSPA.controller;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Reporte;
import cl.duocuc.perfulandia.PerfulandiaSPA.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")

public class ReporteController {
    private final ReporteService reporteService;

    @Autowired
    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/tipo-periodo")
    public ResponseEntity<List<Reporte>> obtenerReportesPorTipoYPeriodo(
            @RequestParam Reporte.TipoReporte tipo,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        try {
            List<Reporte> reportes = reporteService.obtenerReportesPorTipoYPeriodo(tipo, fechaInicio, fechaFin);
            return ResponseEntity.ok(reportes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reporte>> obtenerReportesPorUsuario(
            @PathVariable Integer usuarioId) {
        try {
            List<Reporte> reportes = reporteService.obtenerReportesPorUsuario(usuarioId);
            return ResponseEntity.ok(reportes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<List<Map<String, Object>>> obtenerEstadisticasReportes() {
        try {
            List<Map<String, Object>> estadisticas = reporteService.obtenerEstadisticasReportes();
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Reporte>> obtenerTodosLosReportes() {
        try {
            List<Reporte> reportes = reporteService.obtenerTodosLosReportes();
            return ResponseEntity.ok(reportes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}