package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Registrador;
import com.debloopers.chibchaweb.model.RegistradorDTO;
import com.debloopers.chibchaweb.repos.RegistradorRepository;
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

    public RegistradorDTO get(final String idRegistrador) {
        return registradorRepository.findById(idRegistrador)
                .map(registrador -> mapToDTO(registrador, new RegistradorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final RegistradorDTO registradorDTO) {
        final Registrador registrador = new Registrador();
        mapToEntity(registradorDTO, registrador);
        registrador.setIdRegistrador(registradorDTO.getIdRegistrador());
        return registradorRepository.save(registrador).getIdRegistrador();
    }

    public void update(final String idRegistrador, final RegistradorDTO registradorDTO) {
        final Registrador registrador = registradorRepository.findById(idRegistrador)
                .orElseThrow(NotFoundException::new);
        mapToEntity(registradorDTO, registrador);
        registradorRepository.save(registrador);
    }

    public void delete(final String idRegistrador) {
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

    public boolean idRegistradorExists(final String idRegistrador) {
        return registradorRepository.existsByIdRegistradorIgnoreCase(idRegistrador);
    }

}
