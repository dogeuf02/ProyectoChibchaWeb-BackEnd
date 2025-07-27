package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.domain.*;
import com.debloopers.chibchaweb.model.DistribuidorDTO;
import com.debloopers.chibchaweb.repos.*;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DistribuidorService {

    private final DistribuidorRepository distribuidorRepository;
    private final TipoDocumentoEmpRepository tipoDocumentoEmpRepository;
    private final SolicitudDomDistribuidorRepository solicitudDomDistribuidorRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Transactional
    public String create(final DistribuidorDTO distribuidorDTO) {
        try {

            if (usuarioRepository.findByCorreoUsuario(distribuidorDTO.getCorreoDistribuidor()) != null) {
                return "error: correo ya registrado";
            }

            TipoDocumentoEmp tipoDocumento = tipoDocumentoEmpRepository.findById(distribuidorDTO.getNombreTipoDoc())
                    .orElseThrow(() -> new NotFoundException("Tipo de documento no encontrado"));

            Distribuidor distribuidor = new Distribuidor();
            distribuidor.setNumeroDocEmpresa(distribuidorDTO.getNumeroDocEmpresa());
            distribuidor.setNombreEmpresa(distribuidorDTO.getNombreEmpresa());
            distribuidor.setDireccionEmpresa(distribuidorDTO.getDireccionEmpresa());
            distribuidor.setNombreTipoDoc(tipoDocumento);

            distribuidorRepository.save(distribuidor);

            Usuario usuario = new Usuario();
            usuario.setCorreoUsuario(distribuidorDTO.getCorreoDistribuidor());
            usuario.setContrasena(passwordEncoder.encode(distribuidorDTO.getContrasenaDistribuidor()));
            usuario.setRolUsuario("Distribuidor");

            //La lógica actual maneja una relación de usuario con registrador y no distribuidor!
            //usuario.setDistribuidor(distribuidor);

            usuarioRepository.save(usuario);

            return "ok";
        } catch (Exception e) {
            return "error";
        }
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
        distribuidorDTO.setDireccionEmpresa(distribuidor.getDireccionEmpresa());
        distribuidorDTO.setNombreTipoDoc(distribuidor.getNombreTipoDoc() == null ? null : distribuidor.getNombreTipoDoc().getNombreTipoDoc());
        return distribuidorDTO;
    }

    private Distribuidor mapToEntity(final DistribuidorDTO distribuidorDTO,
            final Distribuidor distribuidor) {
        distribuidor.setNombreEmpresa(distribuidorDTO.getNombreEmpresa());
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
