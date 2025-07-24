package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Administrador;
import com.debloopers.chibchaweb.domain.ClienteDirecto;
import com.debloopers.chibchaweb.domain.Dominio;
import com.debloopers.chibchaweb.domain.Registrador;
import com.debloopers.chibchaweb.domain.SolicitudDomCd;
import com.debloopers.chibchaweb.model.SolicitudDomCdDTO;
import com.debloopers.chibchaweb.repos.AdministradorRepository;
import com.debloopers.chibchaweb.repos.ClienteDirectoRepository;
import com.debloopers.chibchaweb.repos.DominioRepository;
import com.debloopers.chibchaweb.repos.RegistradorRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomCdRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SolicitudDomCdService {

    private final SolicitudDomCdRepository solicitudDomCdRepository;
    private final RegistradorRepository registradorRepository;
    private final DominioRepository dominioRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final AdministradorRepository administradorRepository;

    public SolicitudDomCdService(final SolicitudDomCdRepository solicitudDomCdRepository,
            final RegistradorRepository registradorRepository,
            final DominioRepository dominioRepository,
            final ClienteDirectoRepository clienteDirectoRepository,
            final AdministradorRepository administradorRepository) {
        this.solicitudDomCdRepository = solicitudDomCdRepository;
        this.registradorRepository = registradorRepository;
        this.dominioRepository = dominioRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.administradorRepository = administradorRepository;
    }

    public List<SolicitudDomCdDTO> findAll() {
        final List<SolicitudDomCd> solicitudDomCds = solicitudDomCdRepository.findAll(Sort.by("tld"));
        return solicitudDomCds.stream()
                .map(solicitudDomCd -> mapToDTO(solicitudDomCd, new SolicitudDomCdDTO()))
                .toList();
    }

    public SolicitudDomCdDTO get(final String tld) {
        return solicitudDomCdRepository.findById(tld)
                .map(solicitudDomCd -> mapToDTO(solicitudDomCd, new SolicitudDomCdDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final SolicitudDomCdDTO solicitudDomCdDTO) {
        final SolicitudDomCd solicitudDomCd = new SolicitudDomCd();
        mapToEntity(solicitudDomCdDTO, solicitudDomCd);
        solicitudDomCd.setTld(solicitudDomCdDTO.getTld());
        return solicitudDomCdRepository.save(solicitudDomCd).getTld();
    }

    public void update(final String tld, final SolicitudDomCdDTO solicitudDomCdDTO) {
        final SolicitudDomCd solicitudDomCd = solicitudDomCdRepository.findById(tld)
                .orElseThrow(NotFoundException::new);
        mapToEntity(solicitudDomCdDTO, solicitudDomCd);
        solicitudDomCdRepository.save(solicitudDomCd);
    }

    public void delete(final String tld) {
        solicitudDomCdRepository.deleteById(tld);
    }

    private SolicitudDomCdDTO mapToDTO(final SolicitudDomCd solicitudDomCd,
            final SolicitudDomCdDTO solicitudDomCdDTO) {
        solicitudDomCdDTO.setTld(solicitudDomCd.getTld());
        solicitudDomCdDTO.setFechaSolicitud(solicitudDomCd.getFechaSolicitud());
        solicitudDomCdDTO.setEstadoSolicitud(solicitudDomCd.getEstadoSolicitud());
        solicitudDomCdDTO.setFechaRevision(solicitudDomCd.getFechaRevision());
        solicitudDomCdDTO.setFechaEnvio(solicitudDomCd.getFechaEnvio());
        solicitudDomCdDTO.setRegistrador(solicitudDomCd.getRegistrador() == null ? null : solicitudDomCd.getRegistrador().getIdRegistrador());
        solicitudDomCdDTO.setNombreDominio(solicitudDomCd.getNombreDominio() == null ? null : solicitudDomCd.getNombreDominio().getNombreDominio());
        solicitudDomCdDTO.setCliente(solicitudDomCd.getCliente() == null ? null : solicitudDomCd.getCliente().getIdCliente());
        solicitudDomCdDTO.setAdmin(solicitudDomCd.getAdmin() == null ? null : solicitudDomCd.getAdmin().getIdAdmin());
        return solicitudDomCdDTO;
    }

    private SolicitudDomCd mapToEntity(final SolicitudDomCdDTO solicitudDomCdDTO,
            final SolicitudDomCd solicitudDomCd) {
        solicitudDomCd.setFechaSolicitud(solicitudDomCdDTO.getFechaSolicitud());
        solicitudDomCd.setEstadoSolicitud(solicitudDomCdDTO.getEstadoSolicitud());
        solicitudDomCd.setFechaRevision(solicitudDomCdDTO.getFechaRevision());
        solicitudDomCd.setFechaEnvio(solicitudDomCdDTO.getFechaEnvio());
        final Registrador registrador = solicitudDomCdDTO.getRegistrador() == null ? null : registradorRepository.findById(solicitudDomCdDTO.getRegistrador())
                .orElseThrow(() -> new NotFoundException("registrador not found"));
        solicitudDomCd.setRegistrador(registrador);
        final Dominio nombreDominio = solicitudDomCdDTO.getNombreDominio() == null ? null : dominioRepository.findById(solicitudDomCdDTO.getNombreDominio())
                .orElseThrow(() -> new NotFoundException("nombreDominio not found"));
        solicitudDomCd.setNombreDominio(nombreDominio);
        final ClienteDirecto cliente = solicitudDomCdDTO.getCliente() == null ? null : clienteDirectoRepository.findById(solicitudDomCdDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        solicitudDomCd.setCliente(cliente);
        final Administrador admin = solicitudDomCdDTO.getAdmin() == null ? null : administradorRepository.findById(solicitudDomCdDTO.getAdmin())
                .orElseThrow(() -> new NotFoundException("admin not found"));
        solicitudDomCd.setAdmin(admin);
        return solicitudDomCd;
    }

    public boolean tldExists(final String tld) {
        return solicitudDomCdRepository.existsByTldIgnoreCase(tld);
    }

}
