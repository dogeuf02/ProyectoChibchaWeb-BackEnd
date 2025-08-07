package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.dto.DominioConNombreTldDTO;
import com.debloopers.chibchaweb.dto.DominioDTO;
import com.debloopers.chibchaweb.entity.Dominio;
import com.debloopers.chibchaweb.entity.PerteneceDominio;
import com.debloopers.chibchaweb.entity.SolicitudDominio;
import com.debloopers.chibchaweb.entity.Tld;
import com.debloopers.chibchaweb.repository.DominioRepository;
import com.debloopers.chibchaweb.repository.PerteneceDominioRepository;
import com.debloopers.chibchaweb.repository.SolicitudDominioRepository;
import com.debloopers.chibchaweb.repository.TldRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DominioService {

    private final DominioRepository dominioRepository;
    private final TldRepository tldRepository;
    private final SolicitudDominioRepository solicitudDominioRepository;
    private final PerteneceDominioRepository perteneceDominioRepository;

    public DominioService(final DominioRepository dominioRepository,
            final TldRepository tldRepository,
            final SolicitudDominioRepository solicitudDominioRepository,
            final PerteneceDominioRepository perteneceDominioRepository) {
        this.dominioRepository = dominioRepository;
        this.tldRepository = tldRepository;
        this.solicitudDominioRepository = solicitudDominioRepository;
        this.perteneceDominioRepository = perteneceDominioRepository;
    }

    public List<DominioDTO> findAll() {
        final List<Dominio> dominios = dominioRepository.findAll(Sort.by("idDominio"));
        return dominios.stream()
                .map(dominio -> mapToDTO(dominio, new DominioDTO()))
                .toList();
    }

    public DominioDTO get(final Integer idDominio) {
        return dominioRepository.findById(idDominio)
                .map(dominio -> mapToDTO(dominio, new DominioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final DominioDTO dominioDTO) {
        final Dominio dominio = new Dominio();
        mapToEntity(dominioDTO, dominio);
        return dominioRepository.save(dominio).getIdDominio();
    }

    @Transactional
    public Integer createConPrecioCalculado(final DominioDTO dominioDTO) {
        final Dominio dominio = new Dominio();

        int longitud = dominioDTO.getNombreDominio().length();
        BigDecimal precioPorCaracter = new BigDecimal("0.35");
        BigDecimal precioTotal = precioPorCaracter.multiply(BigDecimal.valueOf(longitud));

        dominioDTO.setPrecioDominio(precioTotal);

        mapToEntity(dominioDTO, dominio);
        return dominioRepository.save(dominio).getIdDominio();
    }

    public BigDecimal calcularPrecioDominioYTld(String dominio, String tld) {
        BigDecimal precioPorCaracter = new BigDecimal("0.35");
        BigDecimal precioDominio = precioPorCaracter.multiply(BigDecimal.valueOf(dominio.length()));

        Tld tldEntity = tldRepository.findById(tld)
                .orElseThrow(() -> new NotFoundException("TLD not found: " + tld));
        BigDecimal precioTld = tldEntity.getPrecioTld();

        return precioDominio.add(precioTld);
    }


    public DominioDTO obtenerDominioPorDTO(DominioConNombreTldDTO dominioInfo) {
        Dominio dominio = dominioRepository
                .findByNombreDominioAndTld_Tld(
                        dominioInfo.getNombreDominio(),
                        dominioInfo.getTldId()
                )
                .orElseThrow(() -> new NotFoundException("Domain not found."));

        return mapToDTO(dominio, new DominioDTO());
    }

    public void update(final Integer idDominio, final DominioDTO dominioDTO) {
        final Dominio dominio = dominioRepository.findById(idDominio)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dominioDTO, dominio);
        dominioRepository.save(dominio);
    }

    public void delete(final Integer idDominio) {
        dominioRepository.deleteById(idDominio);
    }

    private DominioDTO mapToDTO(final Dominio dominio, final DominioDTO dominioDTO) {
        dominioDTO.setIdDominio(dominio.getIdDominio());
        dominioDTO.setNombreDominio(dominio.getNombreDominio());
        dominioDTO.setPrecioDominio(dominio.getPrecioDominio());
        dominioDTO.setEstado(dominio.getEstado());
        dominioDTO.setTld(dominio.getTld() == null ? null : dominio.getTld().getTld());
        return dominioDTO;
    }

    private Dominio mapToEntity(final DominioDTO dominioDTO, final Dominio dominio) {
        dominio.setNombreDominio(dominioDTO.getNombreDominio());
        dominio.setPrecioDominio(dominioDTO.getPrecioDominio());
        dominio.setEstado(dominioDTO.getEstado());
        final Tld tld = dominioDTO.getTld() == null ? null : tldRepository.findById(dominioDTO.getTld())
                .orElseThrow(() -> new NotFoundException("tld not found"));
        dominio.setTld(tld);
        return dominio;
    }

    public ReferencedWarning getReferencedWarning(final Integer idDominio) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Dominio dominio = dominioRepository.findById(idDominio)
                .orElseThrow(NotFoundException::new);
        final SolicitudDominio dominioSolicitudDominio = solicitudDominioRepository.findFirstByDominio(dominio);
        if (dominioSolicitudDominio != null) {
            referencedWarning.setKey("dominio.solicitudDominio.dominio.referenced");
            referencedWarning.addParam(dominioSolicitudDominio.getIdSolicitud());
            return referencedWarning;
        }
        final PerteneceDominio dominioPerteneceDominio = perteneceDominioRepository.findFirstByDominio(dominio);
        if (dominioPerteneceDominio != null) {
            referencedWarning.setKey("dominio.perteneceDominio.dominio.referenced");
            referencedWarning.addParam(dominioPerteneceDominio.getIdPertenece());
            return referencedWarning;
        }
        return null;
    }
}