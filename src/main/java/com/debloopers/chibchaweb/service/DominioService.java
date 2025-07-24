package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Dominio;
import com.debloopers.chibchaweb.domain.SolicitudDomCd;
import com.debloopers.chibchaweb.domain.SolicitudDomDistribuidor;
import com.debloopers.chibchaweb.domain.Tld;
import com.debloopers.chibchaweb.model.DominioDTO;
import com.debloopers.chibchaweb.repos.DominioRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomCdRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomDistribuidorRepository;
import com.debloopers.chibchaweb.repos.TldRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DominioService {

    private final DominioRepository dominioRepository;
    private final TldRepository tldRepository;
    private final SolicitudDomCdRepository solicitudDomCdRepository;
    private final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository;

    public DominioService(final DominioRepository dominioRepository,
            final TldRepository tldRepository,
            final SolicitudDomCdRepository solicitudDomCdRepository,
            final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository) {
        this.dominioRepository = dominioRepository;
        this.tldRepository = tldRepository;
        this.solicitudDomCdRepository = solicitudDomCdRepository;
        this.solicitudDomDistribuidorRepository = solicitudDomDistribuidorRepository;
    }

    public List<DominioDTO> findAll() {
        final List<Dominio> dominios = dominioRepository.findAll(Sort.by("nombreDominio"));
        return dominios.stream()
                .map(dominio -> mapToDTO(dominio, new DominioDTO()))
                .toList();
    }

    public DominioDTO get(final String nombreDominio) {
        return dominioRepository.findById(nombreDominio)
                .map(dominio -> mapToDTO(dominio, new DominioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final DominioDTO dominioDTO) {
        final Dominio dominio = new Dominio();
        mapToEntity(dominioDTO, dominio);
        dominio.setNombreDominio(dominioDTO.getNombreDominio());
        return dominioRepository.save(dominio).getNombreDominio();
    }

    public void update(final String nombreDominio, final DominioDTO dominioDTO) {
        final Dominio dominio = dominioRepository.findById(nombreDominio)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dominioDTO, dominio);
        dominioRepository.save(dominio);
    }

    public void delete(final String nombreDominio) {
        dominioRepository.deleteById(nombreDominio);
    }

    private DominioDTO mapToDTO(final Dominio dominio, final DominioDTO dominioDTO) {
        dominioDTO.setNombreDominio(dominio.getNombreDominio());
        dominioDTO.setTld(dominio.getTld() == null ? null : dominio.getTld().getTld());
        return dominioDTO;
    }

    private Dominio mapToEntity(final DominioDTO dominioDTO, final Dominio dominio) {
        final Tld tld = dominioDTO.getTld() == null ? null : tldRepository.findById(dominioDTO.getTld())
                .orElseThrow(() -> new NotFoundException("tld not found"));
        dominio.setTld(tld);
        return dominio;
    }

    public boolean nombreDominioExists(final String nombreDominio) {
        return dominioRepository.existsByNombreDominioIgnoreCase(nombreDominio);
    }

    public ReferencedWarning getReferencedWarning(final String nombreDominio) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Dominio dominio = dominioRepository.findById(nombreDominio)
                .orElseThrow(NotFoundException::new);
        final SolicitudDomCd nombreDominioSolicitudDomCd = solicitudDomCdRepository.findFirstByNombreDominio(dominio);
        if (nombreDominioSolicitudDomCd != null) {
            referencedWarning.setKey("dominio.solicitudDomCd.nombreDominio.referenced");
            referencedWarning.addParam(nombreDominioSolicitudDomCd.getTld());
            return referencedWarning;
        }
        final SolicitudDomDistribuidor nombreDominioSolicitudDomDistribuidor = solicitudDomDistribuidorRepository.findFirstByNombreDominio(dominio);
        if (nombreDominioSolicitudDomDistribuidor != null) {
            referencedWarning.setKey("dominio.solicitudDomDistribuidor.nombreDominio.referenced");
            referencedWarning.addParam(nombreDominioSolicitudDomDistribuidor.getTld());
            return referencedWarning;
        }
        return null;
    }

}
