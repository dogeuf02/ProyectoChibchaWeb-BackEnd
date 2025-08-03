package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.Distribuidor;
import com.debloopers.chibchaweb.entity.TipoDocumentoEmp;
import com.debloopers.chibchaweb.dto.TipoDocumentoEmpDTO;
import com.debloopers.chibchaweb.repository.DistribuidorRepository;
import com.debloopers.chibchaweb.repository.TipoDocumentoEmpRepository;
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
        final List<TipoDocumentoEmp> tipoDocumentoEmps = tipoDocumentoEmpRepository.findAll(Sort.by("nombreTipoDoc"));
        return tipoDocumentoEmps.stream()
                .map(tipoDocumentoEmp -> mapToDTO(tipoDocumentoEmp, new TipoDocumentoEmpDTO()))
                .toList();
    }

    public TipoDocumentoEmpDTO get(final String nombreTipoDoc) {
        return tipoDocumentoEmpRepository.findById(nombreTipoDoc)
                .map(tipoDocumentoEmp -> mapToDTO(tipoDocumentoEmp, new TipoDocumentoEmpDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final TipoDocumentoEmpDTO tipoDocumentoEmpDTO) {
        final TipoDocumentoEmp tipoDocumentoEmp = new TipoDocumentoEmp();
        mapToEntity(tipoDocumentoEmpDTO, tipoDocumentoEmp);
        tipoDocumentoEmp.setNombreTipoDoc(tipoDocumentoEmpDTO.getNombreTipoDoc());
        return tipoDocumentoEmpRepository.save(tipoDocumentoEmp).getNombreTipoDoc();
    }

    public void update(final String nombreTipoDoc, final TipoDocumentoEmpDTO tipoDocumentoEmpDTO) {
        final TipoDocumentoEmp tipoDocumentoEmp = tipoDocumentoEmpRepository.findById(nombreTipoDoc)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tipoDocumentoEmpDTO, tipoDocumentoEmp);
        tipoDocumentoEmpRepository.save(tipoDocumentoEmp);
    }

    public void delete(final String nombreTipoDoc) {
        tipoDocumentoEmpRepository.deleteById(nombreTipoDoc);
    }

    private TipoDocumentoEmpDTO mapToDTO(final TipoDocumentoEmp tipoDocumentoEmp,
                                         final TipoDocumentoEmpDTO tipoDocumentoEmpDTO) {
        tipoDocumentoEmpDTO.setNombreTipoDoc(tipoDocumentoEmp.getNombreTipoDoc());
        return tipoDocumentoEmpDTO;
    }

    private TipoDocumentoEmp mapToEntity(final TipoDocumentoEmpDTO tipoDocumentoEmpDTO,
                                         final TipoDocumentoEmp tipoDocumentoEmp) {
        return tipoDocumentoEmp;
    }

    public boolean nombreTipoDocExists(final String nombreTipoDoc) {
        return tipoDocumentoEmpRepository.existsByNombreTipoDocIgnoreCase(nombreTipoDoc);
    }

    public ReferencedWarning getReferencedWarning(final String nombreTipoDoc) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final TipoDocumentoEmp tipoDocumentoEmp = tipoDocumentoEmpRepository.findById(nombreTipoDoc)
                .orElseThrow(NotFoundException::new);
        final Distribuidor nombreTipoDocDistribuidor = distribuidorRepository.findFirstByNombreTipoDoc(tipoDocumentoEmp);
        if (nombreTipoDocDistribuidor != null) {
            referencedWarning.setKey("tipoDocumentoEmp.distribuidor.nombreTipoDoc.referenced");
            referencedWarning.addParam(nombreTipoDocDistribuidor.getIdDistribuidor());
            return referencedWarning;
        }
        return null;
    }
}