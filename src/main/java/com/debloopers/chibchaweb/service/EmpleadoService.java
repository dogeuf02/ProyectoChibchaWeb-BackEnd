package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Empleado;
import com.debloopers.chibchaweb.domain.Ticket;
import com.debloopers.chibchaweb.domain.Usuario;
import com.debloopers.chibchaweb.model.EmpleadoDTO;
import com.debloopers.chibchaweb.repos.EmpleadoRepository;
import com.debloopers.chibchaweb.repos.TicketRepository;
import com.debloopers.chibchaweb.repos.UsuarioRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

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

    public Integer create(final EmpleadoDTO empleadoDTO) {
        final Empleado empleado = new Empleado();
        mapToEntity(empleadoDTO, empleado);
        return empleadoRepository.save(empleado).getIdEmpleado();
    }

    public void update(final Integer idEmpleado, final EmpleadoDTO empleadoDTO) {
        final Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(NotFoundException::new);
        mapToEntity(empleadoDTO, empleado);
        empleadoRepository.save(empleado);
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
