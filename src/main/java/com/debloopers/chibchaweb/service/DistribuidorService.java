package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Distribuidor;
import com.debloopers.chibchaweb.domain.TipoDocumentoEmp;
import com.debloopers.chibchaweb.model.DistribuidorDTO;
import com.debloopers.chibchaweb.repos.DistribuidorRepository;
import com.debloopers.chibchaweb.repos.TipoDocumentoEmpRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DistribuidorService {

    private final DistribuidorRepository distribuidorRepository;
    private final TipoDocumentoEmpRepository tipoDocumentoEmpRepository;

    public DistribuidorService(final DistribuidorRepository distribuidorRepository,
            final TipoDocumentoEmpRepository tipoDocumentoEmpRepository) {
        this.distribuidorRepository = distribuidorRepository;
        this.tipoDocumentoEmpRepository = tipoDocumentoEmpRepository;
    }

    public List<DistribuidorDTO> findAll() {
        final List<Distribuidor> distribuidors = distribuidorRepository.findAll(Sort.by("idDistribuidor"));
        return distribuidors.stream()
                .map(distribuidor -> mapToDTO(distribuidor, new DistribuidorDTO()))
                .toList();
    }

    public DistribuidorDTO get(final Integer idDistribuidor) {
        return distribuidorRepository.findById(idDistribuidor)
                .map(distribuidor -> mapToDTO(distribuidor, new DistribuidorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final DistribuidorDTO distribuidorDTO) {
        final Distribuidor distribuidor = new Distribuidor();
        mapToEntity(distribuidorDTO, distribuidor);
        return distribuidorRepository.save(distribuidor).getIdDistribuidor();
    }

    public void update(final Integer idDistribuidor, final DistribuidorDTO distribuidorDTO) {
        final Distribuidor distribuidor = distribuidorRepository.findById(idDistribuidor)
                .orElseThrow(NotFoundException::new);
        mapToEntity(distribuidorDTO, distribuidor);
        distribuidorRepository.save(distribuidor);
    }

    public void delete(final Integer idDistribuidor) {
        distribuidorRepository.deleteById(idDistribuidor);
    }

    private DistribuidorDTO mapToDTO(final Distribuidor distribuidor,
            final DistribuidorDTO distribuidorDTO) {
        distribuidorDTO.setIdDistribuidor(distribuidor.getIdDistribuidor());
        distribuidorDTO.setNumeroDocumento(distribuidor.getNumeroDocumento());
        distribuidorDTO.setNombreEmpresa(distribuidor.getNombreEmpresa());
        distribuidorDTO.setCorreoEmpresa(distribuidor.getCorreoEmpresa());
        distribuidorDTO.setContrasenaEmpresa(distribuidor.getContrasenaEmpresa());
        distribuidorDTO.setDireccionEmpresa(distribuidor.getDireccionEmpresa());
        distribuidorDTO.setTipoDocumento(distribuidor.getTipoDocumento() == null ? null : distribuidor.getTipoDocumento().getIdTipoDocumento());
        return distribuidorDTO;
    }

    private Distribuidor mapToEntity(final DistribuidorDTO distribuidorDTO,
            final Distribuidor distribuidor) {
        distribuidor.setNumeroDocumento(distribuidorDTO.getNumeroDocumento());
        distribuidor.setNombreEmpresa(distribuidorDTO.getNombreEmpresa());
        distribuidor.setCorreoEmpresa(distribuidorDTO.getCorreoEmpresa());
        distribuidor.setContrasenaEmpresa(distribuidorDTO.getContrasenaEmpresa());
        distribuidor.setDireccionEmpresa(distribuidorDTO.getDireccionEmpresa());
        final TipoDocumentoEmp tipoDocumento = distribuidorDTO.getTipoDocumento() == null ? null : tipoDocumentoEmpRepository.findById(distribuidorDTO.getTipoDocumento())
                .orElseThrow(() -> new NotFoundException("tipoDocumento not found"));
        distribuidor.setTipoDocumento(tipoDocumento);
        return distribuidor;
    }

}
