package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Administrador;
import com.debloopers.chibchaweb.domain.ClienteDirecto;
import com.debloopers.chibchaweb.domain.Dominio;
import com.debloopers.chibchaweb.domain.Registrador;
import com.debloopers.chibchaweb.domain.SolicitudDomCliente;
import com.debloopers.chibchaweb.model.SolicitudDomClienteDTO;
import com.debloopers.chibchaweb.repos.AdministradorRepository;
import com.debloopers.chibchaweb.repos.ClienteDirectoRepository;
import com.debloopers.chibchaweb.repos.DominioRepository;
import com.debloopers.chibchaweb.repos.RegistradorRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomClienteRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SolicitudDomClienteService {

    private final SolicitudDomClienteRepository solicitudDomClienteRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final DominioRepository dominioRepository;
    private final AdministradorRepository administradorRepository;
    private final RegistradorRepository registradorRepository;

    public SolicitudDomClienteService(
            final SolicitudDomClienteRepository solicitudDomClienteRepository,
            final ClienteDirectoRepository clienteDirectoRepository,
            final DominioRepository dominioRepository,
            final AdministradorRepository administradorRepository,
            final RegistradorRepository registradorRepository) {
        this.solicitudDomClienteRepository = solicitudDomClienteRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.dominioRepository = dominioRepository;
        this.administradorRepository = administradorRepository;
        this.registradorRepository = registradorRepository;
    }

    public List<SolicitudDomClienteDTO> findAll() {
        final List<SolicitudDomCliente> solicitudDomClientes = solicitudDomClienteRepository.findAll(Sort.by("tld"));
        return solicitudDomClientes.stream()
                .map(solicitudDomCliente -> mapToDTO(solicitudDomCliente, new SolicitudDomClienteDTO()))
                .toList();
    }

    public SolicitudDomClienteDTO get(final String tld) {
        return solicitudDomClienteRepository.findById(tld)
                .map(solicitudDomCliente -> mapToDTO(solicitudDomCliente, new SolicitudDomClienteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final SolicitudDomClienteDTO solicitudDomClienteDTO) {
        final SolicitudDomCliente solicitudDomCliente = new SolicitudDomCliente();
        mapToEntity(solicitudDomClienteDTO, solicitudDomCliente);
        solicitudDomCliente.setTld(solicitudDomClienteDTO.getTld());
        return solicitudDomClienteRepository.save(solicitudDomCliente).getTld();
    }

    public void update(final String tld, final SolicitudDomClienteDTO solicitudDomClienteDTO) {
        final SolicitudDomCliente solicitudDomCliente = solicitudDomClienteRepository.findById(tld)
                .orElseThrow(NotFoundException::new);
        mapToEntity(solicitudDomClienteDTO, solicitudDomCliente);
        solicitudDomClienteRepository.save(solicitudDomCliente);
    }

    public void delete(final String tld) {
        solicitudDomClienteRepository.deleteById(tld);
    }

    private SolicitudDomClienteDTO mapToDTO(final SolicitudDomCliente solicitudDomCliente,
            final SolicitudDomClienteDTO solicitudDomClienteDTO) {
        solicitudDomClienteDTO.setTld(solicitudDomCliente.getTld());
        solicitudDomClienteDTO.setFechaSolicitud(solicitudDomCliente.getFechaSolicitud());
        solicitudDomClienteDTO.setEstadoSolicitud(solicitudDomCliente.getEstadoSolicitud());
        solicitudDomClienteDTO.setFechaRevision(solicitudDomCliente.getFechaRevision());
        solicitudDomClienteDTO.setFechaEnvio(solicitudDomCliente.getFechaEnvio());
        solicitudDomClienteDTO.setCliente(solicitudDomCliente.getCliente() == null ? null : solicitudDomCliente.getCliente().getIdCliente());
        solicitudDomClienteDTO.setNombreDominio(solicitudDomCliente.getNombreDominio() == null ? null : solicitudDomCliente.getNombreDominio().getNombreDominio());
        solicitudDomClienteDTO.setAdmin(solicitudDomCliente.getAdmin() == null ? null : solicitudDomCliente.getAdmin().getIdAdmin());
        solicitudDomClienteDTO.setRegistrador(solicitudDomCliente.getRegistrador() == null ? null : solicitudDomCliente.getRegistrador().getIdRegistrador());
        return solicitudDomClienteDTO;
    }

    private SolicitudDomCliente mapToEntity(final SolicitudDomClienteDTO solicitudDomClienteDTO,
            final SolicitudDomCliente solicitudDomCliente) {
        solicitudDomCliente.setFechaSolicitud(solicitudDomClienteDTO.getFechaSolicitud());
        solicitudDomCliente.setEstadoSolicitud(solicitudDomClienteDTO.getEstadoSolicitud());
        solicitudDomCliente.setFechaRevision(solicitudDomClienteDTO.getFechaRevision());
        solicitudDomCliente.setFechaEnvio(solicitudDomClienteDTO.getFechaEnvio());
        final ClienteDirecto cliente = solicitudDomClienteDTO.getCliente() == null ? null : clienteDirectoRepository.findById(solicitudDomClienteDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        solicitudDomCliente.setCliente(cliente);
        final Dominio nombreDominio = solicitudDomClienteDTO.getNombreDominio() == null ? null : dominioRepository.findById(solicitudDomClienteDTO.getNombreDominio())
                .orElseThrow(() -> new NotFoundException("nombreDominio not found"));
        solicitudDomCliente.setNombreDominio(nombreDominio);
        final Administrador admin = solicitudDomClienteDTO.getAdmin() == null ? null : administradorRepository.findById(solicitudDomClienteDTO.getAdmin())
                .orElseThrow(() -> new NotFoundException("admin not found"));
        solicitudDomCliente.setAdmin(admin);
        final Registrador registrador = solicitudDomClienteDTO.getRegistrador() == null ? null : registradorRepository.findById(solicitudDomClienteDTO.getRegistrador())
                .orElseThrow(() -> new NotFoundException("registrador not found"));
        solicitudDomCliente.setRegistrador(registrador);
        return solicitudDomCliente;
    }

    public boolean tldExists(final String tld) {
        return solicitudDomClienteRepository.existsByTldIgnoreCase(tld);
    }

}
