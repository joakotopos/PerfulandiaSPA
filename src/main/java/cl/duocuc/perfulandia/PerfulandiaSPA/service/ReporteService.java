package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Reporte;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    public List<Reporte> obtenerReportesPorTipoYPeriodo(Reporte.TipoReporte tipo, LocalDate fechaInicio, LocalDate fechaFin) {
        return reporteRepository.findByTipoAndPeriodo(tipo, fechaInicio, fechaFin);
    }

    public List<Reporte> obtenerReportesPorUsuario(Integer usuarioId) {
        return reporteRepository.findByGeneracionUsuario(usuarioId);
    }

    public List<Map<String, Object>> obtenerEstadisticasReportes() {
        return reporteRepository.countReportesPorTipo();
    }

    public List<Reporte> obtenerTodosLosReportes() {
        return reporteRepository.findAll();
    }
}