package cl.duocuc.perfulandia.PerfulandiaSPA.service;

import cl.duocuc.perfulandia.PerfulandiaSPA.model.Sucursal;
import cl.duocuc.perfulandia.PerfulandiaSPA.repository.SucursalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional


public class SucursalService {
    private final SucursalRepository sucursalRepository;

    public Sucursal crearSucursal(Sucursal sucursal) {
        validarHorarios(sucursal.getHorarioapertura(), sucursal.getHorariocierre());
        validarDatosSucursal(sucursal);
        return sucursalRepository.save(sucursal);
    }

    public List<Sucursal> obtenerTodasSucursales() {
        return sucursalRepository.findAll();
    }

    public Optional<Sucursal> obtenerSucursalPorId(Integer id) {
        return sucursalRepository.findById(id);
    }

    public List<Sucursal> obtenerSucursalesPorCiudad(String ciudad) {
        return sucursalRepository.findByCiudad(ciudad);
    }

    public List<Sucursal> obtenerSucursalesConProducto(Integer productoId) {
        return sucursalRepository.findSucursalesConProducto(productoId);
    }

    public List<Sucursal> obtenerSucursalesAbiertas() {
        String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        return sucursalRepository.findSucursalesAbiertas(horaActual);
    }

    public Sucursal actualizarSucursal(Integer id, Sucursal sucursalActualizada) {
        return sucursalRepository.findById(id)
                .map(sucursal -> {
                    validarHorarios(sucursalActualizada.getHorarioapertura(),
                            sucursalActualizada.getHorariocierre());
                    validarDatosSucursal(sucursalActualizada);

                    sucursal.setNombre(sucursalActualizada.getNombre());
                    sucursal.setDireccion(sucursalActualizada.getDireccion());
                    sucursal.setCiudad(sucursalActualizada.getCiudad());
                    sucursal.setHorarioapertura(sucursalActualizada.getHorarioapertura());
                    sucursal.setHorariocierre(sucursalActualizada.getHorariocierre());

                    return sucursalRepository.save(sucursal);
                })
                .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada con ID: " + id));
    }

    public void eliminarSucursal(Integer id) {
        if (!sucursalRepository.existsById(id)) {
            throw new IllegalArgumentException("Sucursal no encontrada con ID: " + id);
        }
        sucursalRepository.deleteById(id);
    }

    private void validarHorarios(String horarioApertura, String horarioCierre) {
        try {
            LocalTime apertura = LocalTime.parse(horarioApertura);
            LocalTime cierre = LocalTime.parse(horarioCierre);

            if (apertura.isAfter(cierre)) {
                throw new IllegalArgumentException("El horario de apertura no puede ser después del horario de cierre");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de horario inválido. Use el formato HH:mm");
        }
    }

    private void validarDatosSucursal(Sucursal sucursal) {
        if (sucursal.getNombre() == null || sucursal.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la sucursal es obligatorio");
        }
        if (sucursal.getDireccion() == null || sucursal.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección de la sucursal es obligatoria");
        }
        if (sucursal.getCiudad() == null || sucursal.getCiudad().trim().isEmpty()) {
            throw new IllegalArgumentException("La ciudad de la sucursal es obligatoria");
        }
    }

    public boolean estaSucursalAbierta(Integer id) {
        String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        return sucursalRepository.findById(id)
                .map(sucursal -> {
                    LocalTime apertura = LocalTime.parse(sucursal.getHorarioapertura());
                    LocalTime cierre = LocalTime.parse(sucursal.getHorariocierre());
                    LocalTime ahora = LocalTime.parse(horaActual);
                    return !ahora.isBefore(apertura) && !ahora.isAfter(cierre);
                })
                .orElse(false);
    }

    public Sucursal actualizarHorarios(Integer id, String nuevoHorarioApertura, String nuevoHorarioCierre) {
        validarHorarios(nuevoHorarioApertura, nuevoHorarioCierre);

        return sucursalRepository.findById(id)
                .map(sucursal -> {
                    sucursal.setHorarioapertura(nuevoHorarioApertura);
                    sucursal.setHorariocierre(nuevoHorarioCierre);
                    return sucursalRepository.save(sucursal);
                })
                .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada con ID: " + id));
    }

}