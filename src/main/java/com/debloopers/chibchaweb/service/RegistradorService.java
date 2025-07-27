package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Registrador;
import com.debloopers.chibchaweb.domain.SolicitudDomCliente;
import com.debloopers.chibchaweb.domain.SolicitudDomDistribuidor;
import com.debloopers.chibchaweb.model.RegistradorDTO;
import com.debloopers.chibchaweb.repos.RegistradorRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomClienteRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomDistribuidorRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RegistradorService {

    private final RegistradorRepository registradorRepository;
    private final SolicitudDomClienteRepository solicitudDomClienteRepository;
    private final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository;

    public RegistradorService(final RegistradorRepository registradorRepository,
            final SolicitudDomClienteRepository solicitudDomClienteRepository,
            final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository) {
        this.registradorRepository = registradorRepository;
        this.solicitudDomClienteRepository = solicitudDomClienteRepository;
        this.solicitudDomDistribuidorRepository = solicitudDomDistribuidorRepository;
    }

    public List<RegistradorDTO> findAll() {
        final List<Registrador> registradors = registradorRepository.findAll(Sort.by("idRegistrador"));
        return registradors.stream()
                .map(registrador -> mapToDTO(registrador, new RegistradorDTO()))
                .toList();
    }

    public RegistradorDTO get(final Integer idRegistrador) {
        return registradorRepository.findById(idRegistrador)
                .map(registrador -> mapToDTO(registrador, new RegistradorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final RegistradorDTO registradorDTO) {
        final Registrador registrador = new Registrador();
        mapToEntity(registradorDTO, registrador);
        return registradorRepository.save(registrador).getIdRegistrador();
    }

    public void update(final Integer idRegistrador, final RegistradorDTO registradorDTO) {
        final Registrador registrador = registradorRepository.findById(idRegistrador)
                .orElseThrow(NotFoundException::new);
        mapToEntity(registradorDTO, registrador);
        registradorRepository.save(registrador);
    }

    public void delete(final Integer idRegistrador) {
        registradorRepository.deleteById(idRegistrador);
    }

    private RegistradorDTO mapToDTO(final Registrador registrador,
            final RegistradorDTO registradorDTO) {
        registradorDTO.setIdRegistrador(registrador.getIdRegistrador());
        registradorDTO.setNombreRegistrador(registrador.getNombreRegistrador());
        return registradorDTO;
    }

    private Registrador mapToEntity(final RegistradorDTO registradorDTO,
            final Registrador registrador) {
        registrador.setNombreRegistrador(registradorDTO.getNombreRegistrador());
        return registrador;
    }

    public ReferencedWarning getReferencedWarning(final Integer idRegistrador) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Registrador registrador = registradorRepository.findById(idRegistrador)
                .orElseThrow(NotFoundException::new);
        final SolicitudDomCliente registradorSolicitudDomCliente = solicitudDomClienteRepository.findFirstByRegistrador(registrador);
        if (registradorSolicitudDomCliente != null) {
            referencedWarning.setKey("registrador.solicitudDomCliente.registrador.referenced");
            referencedWarning.addParam(registradorSolicitudDomCliente.getTld());
            return referencedWarning;
        }
        final SolicitudDomDistribuidor registradorSolicitudDomDistribuidor = solicitudDomDistribuidorRepository.findFirstByRegistrador(registrador);
        if (registradorSolicitudDomDistribuidor != null) {
            referencedWarning.setKey("registrador.solicitudDomDistribuidor.registrador.referenced");
            referencedWarning.addParam(registradorSolicitudDomDistribuidor.getTld());
            return referencedWarning;
        }
        return null;
    }

}
