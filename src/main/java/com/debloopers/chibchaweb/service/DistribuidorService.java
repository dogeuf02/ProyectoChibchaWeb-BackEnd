package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.Distribuidor;
import com.debloopers.chibchaweb.domain.SolicitudDomDistribuidor;
import com.debloopers.chibchaweb.domain.Ticket;
import com.debloopers.chibchaweb.domain.TipoDocumentoEmp;
import com.debloopers.chibchaweb.domain.Usuario;
import com.debloopers.chibchaweb.model.DistribuidorDTO;
import com.debloopers.chibchaweb.repos.DistribuidorRepository;
import com.debloopers.chibchaweb.repos.SolicitudDomDistribuidorRepository;
import com.debloopers.chibchaweb.repos.TicketRepository;
import com.debloopers.chibchaweb.repos.TipoDocumentoEmpRepository;
import com.debloopers.chibchaweb.repos.UsuarioRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DistribuidorService {

    private final DistribuidorRepository distribuidorRepository;
    private final TipoDocumentoEmpRepository tipoDocumentoEmpRepository;
    private final UsuarioRepository usuarioRepository;
    private final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository;
    private final TicketRepository ticketRepository;

    public DistribuidorService(final DistribuidorRepository distribuidorRepository,
            final TipoDocumentoEmpRepository tipoDocumentoEmpRepository,
            final UsuarioRepository usuarioRepository,
            final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository,
            final TicketRepository ticketRepository) {
        this.distribuidorRepository = distribuidorRepository;
        this.tipoDocumentoEmpRepository = tipoDocumentoEmpRepository;
        this.usuarioRepository = usuarioRepository;
        this.solicitudDomDistribuidorRepository = solicitudDomDistribuidorRepository;
        this.ticketRepository = ticketRepository;
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
        distribuidorDTO.setNumeroDocEmpresa(distribuidor.getNumeroDocEmpresa());
        distribuidorDTO.setNombreEmpresa(distribuidor.getNombreEmpresa());
        distribuidorDTO.setDireccionEmpresa(distribuidor.getDireccionEmpresa());
        distribuidorDTO.setNombreTipoDoc(distribuidor.getNombreTipoDoc() == null ? null : distribuidor.getNombreTipoDoc().getNombreTipoDoc());
        return distribuidorDTO;
    }

    private Distribuidor mapToEntity(final DistribuidorDTO distribuidorDTO,
            final Distribuidor distribuidor) {
        distribuidor.setNumeroDocEmpresa(distribuidorDTO.getNumeroDocEmpresa());
        distribuidor.setNombreEmpresa(distribuidorDTO.getNombreEmpresa());
        distribuidor.setDireccionEmpresa(distribuidorDTO.getDireccionEmpresa());
        final TipoDocumentoEmp nombreTipoDoc = distribuidorDTO.getNombreTipoDoc() == null ? null : tipoDocumentoEmpRepository.findById(distribuidorDTO.getNombreTipoDoc())
                .orElseThrow(() -> new NotFoundException("nombreTipoDoc not found"));
        distribuidor.setNombreTipoDoc(nombreTipoDoc);
        return distribuidor;
    }

    public ReferencedWarning getReferencedWarning(final Integer idDistribuidor) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Distribuidor distribuidor = distribuidorRepository.findById(idDistribuidor)
                .orElseThrow(NotFoundException::new);
        final Usuario distribuidorUsuario = usuarioRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorUsuario != null) {
            referencedWarning.setKey("distribuidor.usuario.distribuidor.referenced");
            referencedWarning.addParam(distribuidorUsuario.getIdUsuario());
            return referencedWarning;
        }
        final SolicitudDomDistribuidor distribuidorSolicitudDomDistribuidor = solicitudDomDistribuidorRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorSolicitudDomDistribuidor != null) {
            referencedWarning.setKey("distribuidor.solicitudDomDistribuidor.distribuidor.referenced");
            referencedWarning.addParam(distribuidorSolicitudDomDistribuidor.getTld());
            return referencedWarning;
        }
        final Ticket distribuidorTicket = ticketRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorTicket != null) {
            referencedWarning.setKey("distribuidor.ticket.distribuidor.referenced");
            referencedWarning.addParam(distribuidorTicket.getIdTicket());
            return referencedWarning;
        }
        return null;
    }

}
