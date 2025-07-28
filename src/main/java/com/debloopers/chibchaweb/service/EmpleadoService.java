package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Empleado;
import com.debloopers.chibchaweb.domain.Ticket;
import com.debloopers.chibchaweb.domain.Usuario;
import com.debloopers.chibchaweb.model.*;
import com.debloopers.chibchaweb.repos.EmpleadoRepository;
import com.debloopers.chibchaweb.repos.TicketRepository;
import com.debloopers.chibchaweb.repos.UsuarioRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmpleadoService(final EmpleadoRepository empleadoRepository,
            final TicketRepository ticketRepository, final UsuarioRepository usuarioRepository) {
        this.empleadoRepository = empleadoRepository;
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
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

    public EmpleadoRegistroResponseDTO create(final EmpleadoRegistroRequestDTO dto) {

        if (usuarioRepository.findByCorreoUsuario(dto.getCorreo()) != null) {
            return new EmpleadoRegistroResponseDTO(false, "The email is already registered.");
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

            return new EmpleadoRegistroResponseDTO(true, "Employee successfully registered.");

        } catch (Exception e) {
            return new EmpleadoRegistroResponseDTO(false, "Error registering employee: " + e.getMessage());
        }
    }

    public void update(final Integer idEmpleado, final EmpleadoActualizarDTO empleadoDTO) {
        final Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(NotFoundException::new);

        if (empleadoDTO.getNombreEmpleado() != null && !empleadoDTO.getNombreEmpleado().isBlank()) {
            empleado.setNombreEmpleado(empleadoDTO.getNombreEmpleado().trim());
        }

        if (empleadoDTO.getApellidoEmpleado() != null && !empleadoDTO.getApellidoEmpleado().isBlank()) {
            empleado.setApellidoEmpleado(empleadoDTO.getApellidoEmpleado().trim());
        }

        if (empleadoDTO.getCargoEmpleado() != null && !empleadoDTO.getCargoEmpleado().isBlank()) {
            empleado.setCargoEmpleado(empleadoDTO.getCargoEmpleado().trim());
        }

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
        final Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        ticketRepository.findAllByHistorialTicketUsuarioEmpleadoes(empleado)
                .forEach(ticket -> ticket.getHistorialTicketUsuarioEmpleadoes().remove(empleado));
        empleadoRepository.delete(empleado);
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
        final Ticket empleadoTicket = ticketRepository.findFirstByEmpleado(empleado);
        if (empleadoTicket != null) {
            referencedWarning.setKey("empleado.ticket.empleado.referenced");
            referencedWarning.addParam(empleadoTicket.getIdTicket());
            return referencedWarning;
        }
        return null;
    }

}
