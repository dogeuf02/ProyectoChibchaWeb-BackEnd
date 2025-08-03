package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.Tld;
import com.debloopers.chibchaweb.dto.TldDTO;
import com.debloopers.chibchaweb.repository.DominioRepository;
import com.debloopers.chibchaweb.repository.TldRepository;
import com.debloopers.chibchaweb.util.NotFoundException;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TldService {

    private final TldRepository tldRepository;

    public TldService(final TldRepository tldRepository,
                      final DominioRepository dominioRepository) {
        this.tldRepository = tldRepository;
    }

    public List<TldDTO> findAll() {
        final List<Tld> tlds = tldRepository.findAll(Sort.by("tld"));
        return tlds.stream()
                .map(tld -> mapToDTO(tld, new TldDTO()))
                .toList();
    }

    public TldDTO get(final String tld) {
        return tldRepository.findById(tld)
                .map(tldEntity -> mapToDTO(tldEntity, new TldDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final TldDTO tldDTO) {
        final Tld tld = new Tld();
        mapToEntity(tldDTO, tld);
        tld.setTld(tldDTO.getTld());
        return tldRepository.save(tld).getTld();
    }

    public void update(final String tld, final TldDTO tldDTO) {
        final Tld tldEntity = tldRepository.findById(tld)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tldDTO, tldEntity);
        tldRepository.save(tldEntity);
    }

    public void delete(final String tld) {
        tldRepository.deleteById(tld);
    }

    private TldDTO mapToDTO(final Tld tld, final TldDTO tldDTO) {
        tldDTO.setTld(tld.getTld());
        tldDTO.setPrecioTld(tld.getPrecioTld());
        return tldDTO;
    }

    private Tld mapToEntity(final TldDTO tldDTO, final Tld tld) {
        tld.setPrecioTld(tldDTO.getPrecioTld());
        return tld;
    }

    public boolean tldExists(final String tld) {
        return tldRepository.existsByTldIgnoreCase(tld);
    }
}