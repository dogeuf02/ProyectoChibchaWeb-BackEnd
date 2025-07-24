package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Distribuidor;
import com.debloopers.chibchaweb.domain.SolicitudDomDistribuidor;
import com.debloopers.chibchaweb.domain.Ticket;
import com.debloopers.chibchaweb.domain.TipoDocumentoEmp;
import com.debloopers.chibchaweb.model.DistribuidorDTO;
import com.debloopers.chibchaweb.repos.DistribuidorRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomDistribuidorRepository;
import com.debloopers.chibchaweb.repos.TicketRepository;
import com.debloopers.chibchaweb.repos.TipoDocumentoEmpRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DistribuidorService {

    private final DistribuidorRepository distribuidorRepository;
    private final TipoDocumentoEmpRepository tipoDocumentoEmpRepository;
    private final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository;
    private final TicketRepository ticketRepository;

    public DistribuidorService(final DistribuidorRepository distribuidorRepository,
            final TipoDocumentoEmpRepository tipoDocumentoEmpRepository,
            final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository,
            final TicketRepository ticketRepository) {
        this.distribuidorRepository = distribuidorRepository;
        this.tipoDocumentoEmpRepository = tipoDocumentoEmpRepository;
        this.solicitudDomDistribuidorRepository = solicitudDomDistribuidorRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<DistribuidorDTO> findAll() {
        final List<Distribuidor> distribuidors = distribuidorRepository.findAll(Sort.by("numeroDocEmpresa"));
        return distribuidors.stream()
                .map(distribuidor -> mapToDTO(distribuidor, new DistribuidorDTO()))
                .toList();
    }

    public DistribuidorDTO get(final String numeroDocEmpresa) {
        return distribuidorRepository.findById(numeroDocEmpresa)
                .map(distribuidor -> mapToDTO(distribuidor, new DistribuidorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final DistribuidorDTO distribuidorDTO) {
        final Distribuidor distribuidor = new Distribuidor();
        mapToEntity(distribuidorDTO, distribuidor);
        distribuidor.setNumeroDocEmpresa(distribuidorDTO.getNumeroDocEmpresa());
        return distribuidorRepository.save(distribuidor).getNumeroDocEmpresa();
    }

    public void update(final String numeroDocEmpresa, final DistribuidorDTO distribuidorDTO) {
        final Distribuidor distribuidor = distribuidorRepository.findById(numeroDocEmpresa)
                .orElseThrow(NotFoundException::new);
        mapToEntity(distribuidorDTO, distribuidor);
        distribuidorRepository.save(distribuidor);
    }

    public void delete(final String numeroDocEmpresa) {
        distribuidorRepository.deleteById(numeroDocEmpresa);
    }

    private DistribuidorDTO mapToDTO(final Distribuidor distribuidor,
            final DistribuidorDTO distribuidorDTO) {
        distribuidorDTO.setNumeroDocEmpresa(distribuidor.getNumeroDocEmpresa());
        distribuidorDTO.setNombreEmpresa(distribuidor.getNombreEmpresa());
        distribuidorDTO.setCorreoEmpresa(distribuidor.getCorreoEmpresa());
        distribuidorDTO.setContrasenaEmpresa(distribuidor.getContrasenaEmpresa());
        distribuidorDTO.setDireccionEmpresa(distribuidor.getDireccionEmpresa());
        distribuidorDTO.setNombreTipoDoc(distribuidor.getNombreTipoDoc() == null ? null : distribuidor.getNombreTipoDoc().getNombreTipoDoc());
        return distribuidorDTO;
    }

    private Distribuidor mapToEntity(final DistribuidorDTO distribuidorDTO,
            final Distribuidor distribuidor) {
        distribuidor.setNombreEmpresa(distribuidorDTO.getNombreEmpresa());
        distribuidor.setCorreoEmpresa(distribuidorDTO.getCorreoEmpresa());
        distribuidor.setContrasenaEmpresa(distribuidorDTO.getContrasenaEmpresa());
        distribuidor.setDireccionEmpresa(distribuidorDTO.getDireccionEmpresa());
        final TipoDocumentoEmp nombreTipoDoc = distribuidorDTO.getNombreTipoDoc() == null ? null : tipoDocumentoEmpRepository.findById(distribuidorDTO.getNombreTipoDoc())
                .orElseThrow(() -> new NotFoundException("nombreTipoDoc not found"));
        distribuidor.setNombreTipoDoc(nombreTipoDoc);
        return distribuidor;
    }

    public boolean numeroDocEmpresaExists(final String numeroDocEmpresa) {
        return distribuidorRepository.existsByNumeroDocEmpresaIgnoreCase(numeroDocEmpresa);
    }

    public ReferencedWarning getReferencedWarning(final String numeroDocEmpresa) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Distribuidor distribuidor = distribuidorRepository.findById(numeroDocEmpresa)
                .orElseThrow(NotFoundException::new);
        final SolicitudDomDistribuidor nombreTipoDocSolicitudDomDistribuidor = solicitudDomDistribuidorRepository.findFirstByNombreTipoDoc(distribuidor);
        if (nombreTipoDocSolicitudDomDistribuidor != null) {
            referencedWarning.setKey("distribuidor.solicitudDomDistribuidor.nombreTipoDoc.referenced");
            referencedWarning.addParam(nombreTipoDocSolicitudDomDistribuidor.getTld());
            return referencedWarning;
        }
        final Ticket nombreTipoDocTicket = ticketRepository.findFirstByNombreTipoDoc(distribuidor);
        if (nombreTipoDocTicket != null) {
            referencedWarning.setKey("distribuidor.ticket.nombreTipoDoc.referenced");
            referencedWarning.addParam(nombreTipoDocTicket.getIdTicket());
            return referencedWarning;
        }
        return null;
    }

}
