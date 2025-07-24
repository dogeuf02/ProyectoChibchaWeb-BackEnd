package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Distribuidor;
import com.debloopers.chibchaweb.domain.TipoDocumentoEmp;
import com.debloopers.chibchaweb.model.TipoDocumentoEmpDTO;
import com.debloopers.chibchaweb.repos.DistribuidorRepository;
import com.debloopers.chibchaweb.repos.TipoDocumentoEmpRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TipoDocumentoEmpService {

    private final TipoDocumentoEmpRepository tipoDocumentoEmpRepository;
    private final DistribuidorRepository distribuidorRepository;

    public TipoDocumentoEmpService(final TipoDocumentoEmpRepository tipoDocumentoEmpRepository,
            final DistribuidorRepository distribuidorRepository) {
        this.tipoDocumentoEmpRepository = tipoDocumentoEmpRepository;
        this.distribuidorRepository = distribuidorRepository;
    }

    public List<TipoDocumentoEmpDTO> findAll() {
        final List<TipoDocumentoEmp> tipoDocumentoEmps = tipoDocumentoEmpRepository.findAll(Sort.by("idTipoDocumento"));
        return tipoDocumentoEmps.stream()
                .map(tipoDocumentoEmp -> mapToDTO(tipoDocumentoEmp, new TipoDocumentoEmpDTO()))
                .toList();
    }

    public TipoDocumentoEmpDTO get(final Integer idTipoDocumento) {
        return tipoDocumentoEmpRepository.findById(idTipoDocumento)
                .map(tipoDocumentoEmp -> mapToDTO(tipoDocumentoEmp, new TipoDocumentoEmpDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TipoDocumentoEmpDTO tipoDocumentoEmpDTO) {
        final TipoDocumentoEmp tipoDocumentoEmp = new TipoDocumentoEmp();
        mapToEntity(tipoDocumentoEmpDTO, tipoDocumentoEmp);
        return tipoDocumentoEmpRepository.save(tipoDocumentoEmp).getIdTipoDocumento();
    }

    public void update(final Integer idTipoDocumento,
            final TipoDocumentoEmpDTO tipoDocumentoEmpDTO) {
        final TipoDocumentoEmp tipoDocumentoEmp = tipoDocumentoEmpRepository.findById(idTipoDocumento)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tipoDocumentoEmpDTO, tipoDocumentoEmp);
        tipoDocumentoEmpRepository.save(tipoDocumentoEmp);
    }

    public void delete(final Integer idTipoDocumento) {
        tipoDocumentoEmpRepository.deleteById(idTipoDocumento);
    }

    private TipoDocumentoEmpDTO mapToDTO(final TipoDocumentoEmp tipoDocumentoEmp,
            final TipoDocumentoEmpDTO tipoDocumentoEmpDTO) {
        tipoDocumentoEmpDTO.setIdTipoDocumento(tipoDocumentoEmp.getIdTipoDocumento());
        tipoDocumentoEmpDTO.setNombreTipo(tipoDocumentoEmp.getNombreTipo());
        return tipoDocumentoEmpDTO;
    }

    private TipoDocumentoEmp mapToEntity(final TipoDocumentoEmpDTO tipoDocumentoEmpDTO,
            final TipoDocumentoEmp tipoDocumentoEmp) {
        tipoDocumentoEmp.setNombreTipo(tipoDocumentoEmpDTO.getNombreTipo());
        return tipoDocumentoEmp;
    }

    public ReferencedWarning getReferencedWarning(final Integer idTipoDocumento) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TipoDocumentoEmp tipoDocumentoEmp = tipoDocumentoEmpRepository.findById(idTipoDocumento)
                .orElseThrow(NotFoundException::new);
        final Distribuidor tipoDocumentoDistribuidor = distribuidorRepository.findFirstByTipoDocumento(tipoDocumentoEmp);
        if (tipoDocumentoDistribuidor != null) {
            referencedWarning.setKey("tipoDocumentoEmp.distribuidor.tipoDocumento.referenced");
            referencedWarning.addParam(tipoDocumentoDistribuidor.getIdDistribuidor());
            return referencedWarning;
        }
        return null;
    }

}
