package cl.duocuc.perfulandia.PerfulandiaSPA.repository;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    @Query("SELECT r FROM Reporte r WHERE r.tipo = :tipo AND r.fechageneracion BETWEEN :inicio AND :fin")
    List<Reporte> findByTipoAndPeriodo(
            @Param("tipo") Reporte.TipoReporte tipo,
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin);

    @Query("SELECT r FROM Reporte r WHERE r.Generadopor.idusuario = :usuarioId")
    List<Reporte> findByGeneracionUsuario(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT NEW map(r.tipo as tipo, COUNT(r) as cantidad) FROM Reporte r GROUP BY r.tipo")
    List<Map<String, Object>> countReportesPorTipo();

}
