package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.MetodoPago;
import com.debloopers.chibchaweb.model.MetodoPagoDTO;
import com.debloopers.chibchaweb.repos.MetodoPagoRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    public MetodoPagoService(final MetodoPagoRepository metodoPagoRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public List<MetodoPagoDTO> findAll() {
        final List<MetodoPago> metodoPagoes = metodoPagoRepository.findAll(Sort.by("idMetodoPago"));
        return metodoPagoes.stream()
                .map(metodoPago -> mapToDTO(metodoPago, new MetodoPagoDTO()))
                .toList();
    }

    public MetodoPagoDTO get(final Integer idMetodoPago) {
        return metodoPagoRepository.findById(idMetodoPago)
                .map(metodoPago -> mapToDTO(metodoPago, new MetodoPagoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MetodoPagoDTO metodoPagoDTO) {
        final MetodoPago metodoPago = new MetodoPago();
        mapToEntity(metodoPagoDTO, metodoPago);
        return metodoPagoRepository.save(metodoPago).getIdMetodoPago();
    }

    public void update(final Integer idMetodoPago, final MetodoPagoDTO metodoPagoDTO) {
        final MetodoPago metodoPago = metodoPagoRepository.findById(idMetodoPago)
                .orElseThrow(NotFoundException::new);
        mapToEntity(metodoPagoDTO, metodoPago);
        metodoPagoRepository.save(metodoPago);
    }

    public void delete(final Integer idMetodoPago) {
        metodoPagoRepository.deleteById(idMetodoPago);
    }

    private MetodoPagoDTO mapToDTO(final MetodoPago metodoPago, final MetodoPagoDTO metodoPagoDTO) {
        metodoPagoDTO.setIdMetodoPago(metodoPago.getIdMetodoPago());
        metodoPagoDTO.setNombreTitular(metodoPago.getNombreTitular());
        metodoPagoDTO.setMarcaTarjeta(metodoPago.getMarcaTarjeta());
        metodoPagoDTO.setNumeroTarjeta(metodoPago.getNumeroTarjeta());
        metodoPagoDTO.setTipoTarjeta(metodoPago.getTipoTarjeta());
        metodoPagoDTO.setCvv(metodoPago.getCvv());
        metodoPagoDTO.setMesExpiracion(metodoPago.getMesExpiracion());
        metodoPagoDTO.setAnoExpiracion(metodoPago.getAnoExpiracion());
        return metodoPagoDTO;
    }

    private MetodoPago mapToEntity(final MetodoPagoDTO metodoPagoDTO, final MetodoPago metodoPago) {
        metodoPago.setNombreTitular(metodoPagoDTO.getNombreTitular());
        metodoPago.setMarcaTarjeta(metodoPagoDTO.getMarcaTarjeta());
        metodoPago.setNumeroTarjeta(metodoPagoDTO.getNumeroTarjeta());
        metodoPago.setTipoTarjeta(metodoPagoDTO.getTipoTarjeta());
        metodoPago.setCvv(metodoPagoDTO.getCvv());
        metodoPago.setMesExpiracion(metodoPagoDTO.getMesExpiracion());
        metodoPago.setAnoExpiracion(metodoPagoDTO.getAnoExpiracion());
        return metodoPago;
    }

}
