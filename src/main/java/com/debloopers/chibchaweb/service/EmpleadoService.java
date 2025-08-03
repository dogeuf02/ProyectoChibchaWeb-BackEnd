package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.Empleado;
import com.debloopers.chibchaweb.entity.HistorialTicket;
import com.debloopers.chibchaweb.entity.Usuario;
import com.debloopers.chibchaweb.dto.*;
import com.debloopers.chibchaweb.repository.EmpleadoRepository;
import com.debloopers.chibchaweb.repository.HistorialTicketRepository;
import com.debloopers.chibchaweb.repository.UsuarioRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final HistorialTicketRepository historialTicketRepository;

    private final PasswordEncoder passwordEncoder;

    public EmpleadoService(final EmpleadoRepository empleadoRepository,
                           final UsuarioRepository usuarioRepository,
                           final HistorialTicketRepository historialTicketRepository,final PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.historialTicketRepository = historialTicketRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<EmpleadoDTO> findAll() {
        final List<Empleado> empleadoes = empleadoRepository.findAll(Sort.by("idEmpleado"));
        return empleadoes.stream()
                .map(empleado -> mapToDTO(empleado, new EmpleadoDTO()))
                .toList();
    }

    public EmpleadoDTO get(final Integer idEmpleado) {
        return empleadoRepository.findById(idEmpleado)
                .map(empleado -> mapToDTO(empleado, new EmpleadoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public ResponseDTO create(final EmpleadoRegistroRequestDTO dto) {

        if (usuarioRepository.findByCorreoUsuario(dto.getCorreo()) != null) {
            return new ResponseDTO(false, "The email is already registered.");
        }

        try {

            final Empleado empleado = new Empleado();
            empleado.setNombreEmpleado(dto.getNombreEmpleado());
            empleado.setApellidoEmpleado(dto.getApellidoEmpleado());
            empleado.setCargoEmpleado(dto.getCargoEmpleado());

            final Empleado empleadoCreado = empleadoRepository.save(empleado);

            Usuario usuario = new Usuario();
            usuario.setCorreoUsuario(dto.getCorreo());
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
            usuario.setRol("Empleado");
            usuario.setEstado("ACTIVO");
            usuario.setEmpleado(empleadoCreado);
            usuarioRepository.save(usuario);

            return new ResponseDTO(true, "Employee successfully registered.");

        } catch (Exception e) {
            return new ResponseDTO(false, "Error registering employee: " + e.getMessage());
        }
    }

    public void update(final Integer idEmpleado, final EmpleadoDTO empleadoDTO) {
        final Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(NotFoundException::new);
        mapToEntity(empleadoDTO, empleado);
        empleadoRepository.save(empleado);
    }

    public List<EmpleadoConCorreoDTO> findAllWithCorreo() {
        List<Empleado> empleados = empleadoRepository.findAll(Sort.by("idEmpleado"));

        return empleados.stream().map(empleado -> {
            Usuario usuario = usuarioRepository.findFirstByEmpleado(empleado);

            EmpleadoConCorreoDTO dto = new EmpleadoConCorreoDTO();
            dto.setIdEmpleado(empleado.getIdEmpleado());
            dto.setNombreEmpleado(empleado.getNombreEmpleado());
            dto.setApellidoEmpleado(empleado.getApellidoEmpleado());
            dto.setCargoEmpleado(empleado.getCargoEmpleado());
            dto.setCorreo(usuario != null ? usuario.getCorreoUsuario() : null);
            dto.setEstado(usuario != null ? usuario.getEstado() : null);

            return dto;
        }).toList();
    }

    public void delete(final Integer idEmpleado) {
        empleadoRepository.deleteById(idEmpleado);
    }

    private EmpleadoDTO mapToDTO(final Empleado empleado, final EmpleadoDTO empleadoDTO) {
        empleadoDTO.setIdEmpleado(empleado.getIdEmpleado());
        empleadoDTO.setNombreEmpleado(empleado.getNombreEmpleado());
        empleadoDTO.setApellidoEmpleado(empleado.getApellidoEmpleado());
        empleadoDTO.setCargoEmpleado(empleado.getCargoEmpleado());
        return empleadoDTO;
    }

    private Empleado mapToEntity(final EmpleadoDTO empleadoDTO, final Empleado empleado) {
        empleado.setNombreEmpleado(empleadoDTO.getNombreEmpleado());
        empleado.setApellidoEmpleado(empleadoDTO.getApellidoEmpleado());
        empleado.setCargoEmpleado(empleadoDTO.getCargoEmpleado());
        return empleado;
    }

    public ReferencedWarning getReferencedWarning(final Integer idEmpleado) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(NotFoundException::new);
        final Usuario empleadoUsuario = usuarioRepository.findFirstByEmpleado(empleado);
        if (empleadoUsuario != null) {
            referencedWarning.setKey("empleado.usuario.empleado.referenced");
            referencedWarning.addParam(empleadoUsuario.getIdUsuario());
            return referencedWarning;
        }
        final HistorialTicket empleadoRealizadorHistorialTicket = historialTicketRepository.findFirstByEmpleadoRealizador(empleado);
        if (empleadoRealizadorHistorialTicket != null) {
            referencedWarning.setKey("empleado.historialTicket.empleadoRealizador.referenced");
            referencedWarning.addParam(empleadoRealizadorHistorialTicket.getIdHistorialTicket());
            return referencedWarning;
        }
        final HistorialTicket empleadoReceptorHistorialTicket = historialTicketRepository.findFirstByEmpleadoReceptor(empleado);
        if (empleadoReceptorHistorialTicket != null) {
            referencedWarning.setKey("empleado.historialTicket.empleadoReceptor.referenced");
            referencedWarning.addParam(empleadoReceptorHistorialTicket.getIdHistorialTicket());
            return referencedWarning;
        }
        return null;
    }
}