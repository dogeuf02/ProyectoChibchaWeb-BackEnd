package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.Registrador;
import com.debloopers.chibchaweb.dto.RegistradorDTO;
import com.debloopers.chibchaweb.repository.RegistradorRepository;
import com.debloopers.chibchaweb.util.NotFoundException;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RegistradorService {

    private final RegistradorRepository registradorRepository;

    public RegistradorService(final RegistradorRepository registradorRepository) {
        this.registradorRepository = registradorRepository;
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
        registradorDTO.setCorreoRegistrador(registrador.getCorreoRegistrador());
        return registradorDTO;
    }

    private Registrador mapToEntity(final RegistradorDTO registradorDTO,
                                    final Registrador registrador) {
        registrador.setNombreRegistrador(registradorDTO.getNombreRegistrador());
        registrador.setCorreoRegistrador(registradorDTO.getCorreoRegistrador());
        return registrador;
    }

}
