package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Empleado;
import com.debloopers.chibchaweb.model.EmpleadoDTO;
import com.debloopers.chibchaweb.repos.EmpleadoRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(final EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<EmpleadoDTO> findAll() {
        final List<Empleado> empleadoes = empleadoRepository.findAll(Sort.by("idEmpleado"));
        return empleadoes.stream()
                .map(empleado -> mapToDTO(empleado, new EmpleadoDTO()))
                .toList();
    }

    public EmpleadoDTO get(final String idEmpleado) {
        return empleadoRepository.findById(idEmpleado)
                .map(empleado -> mapToDTO(empleado, new EmpleadoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final EmpleadoDTO empleadoDTO) {
        final Empleado empleado = new Empleado();
        mapToEntity(empleadoDTO, empleado);
        empleado.setIdEmpleado(empleadoDTO.getIdEmpleado());
        return empleadoRepository.save(empleado).getIdEmpleado();
    }

    public void update(final String idEmpleado, final EmpleadoDTO empleadoDTO) {
        final Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(NotFoundException::new);
        mapToEntity(empleadoDTO, empleado);
        empleadoRepository.save(empleado);
    }

    public void delete(final String idEmpleado) {
        empleadoRepository.deleteById(idEmpleado);
    }

    private EmpleadoDTO mapToDTO(final Empleado empleado, final EmpleadoDTO empleadoDTO) {
        empleadoDTO.setIdEmpleado(empleado.getIdEmpleado());
        empleadoDTO.setCorreoEmpleado(empleado.getCorreoEmpleado());
        empleadoDTO.setContrasenaEmpleado(empleado.getContrasenaEmpleado());
        empleadoDTO.setNombreEmpleado(empleado.getNombreEmpleado());
        empleadoDTO.setApellidoEmpleado(empleado.getApellidoEmpleado());
        empleadoDTO.setCargoEmpleado(empleado.getCargoEmpleado());
        empleadoDTO.setFechaNacimientoEmpleado(empleado.getFechaNacimientoEmpleado());
        return empleadoDTO;
    }

    private Empleado mapToEntity(final EmpleadoDTO empleadoDTO, final Empleado empleado) {
        empleado.setCorreoEmpleado(empleadoDTO.getCorreoEmpleado());
        empleado.setContrasenaEmpleado(empleadoDTO.getContrasenaEmpleado());
        empleado.setNombreEmpleado(empleadoDTO.getNombreEmpleado());
        empleado.setApellidoEmpleado(empleadoDTO.getApellidoEmpleado());
        empleado.setCargoEmpleado(empleadoDTO.getCargoEmpleado());
        empleado.setFechaNacimientoEmpleado(empleadoDTO.getFechaNacimientoEmpleado());
        return empleado;
    }

    public boolean idEmpleadoExists(final String idEmpleado) {
        return empleadoRepository.existsByIdEmpleadoIgnoreCase(idEmpleado);
    }

}
