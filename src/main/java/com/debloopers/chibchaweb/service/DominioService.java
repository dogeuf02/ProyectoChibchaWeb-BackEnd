package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Dominio;
import com.debloopers.chibchaweb.domain.Tld;
import com.debloopers.chibchaweb.model.DominioDTO;
import com.debloopers.chibchaweb.repos.DominioRepository;
import com.debloopers.chibchaweb.repos.TldRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DominioService {

    private final DominioRepository dominioRepository;
    private final TldRepository tldRepository;

    public DominioService(final DominioRepository dominioRepository,
            final TldRepository tldRepository) {
        this.dominioRepository = dominioRepository;
        this.tldRepository = tldRepository;
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

}
