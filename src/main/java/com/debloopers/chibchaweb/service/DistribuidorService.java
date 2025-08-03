package com.debloopers.chibchaweb.service;

import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.dto.*;
import com.debloopers.chibchaweb.repository.*;
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
    private final CategoriaDistribuidorRepository categoriaDistribuidorRepository;
    private final UsuarioRepository usuarioRepository;
    private final TicketRepository ticketRepository;
    private final SolicitudDominioRepository solicitudDominioRepository;
    private final MedioPagoRepository medioPagoRepository;
    private final ComisionRepository comisionRepository;
    private final PerteneceDominioRepository perteneceDominioRepository;
    private final SolicitudTrasladoRepository solicitudTrasladoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public DistribuidorService(final DistribuidorRepository distribuidorRepository,
                               final TipoDocumentoEmpRepository tipoDocumentoEmpRepository,
                               final CategoriaDistribuidorRepository categoriaDistribuidorRepository,
                               final UsuarioRepository usuarioRepository, final TicketRepository ticketRepository,
                               final SolicitudDominioRepository solicitudDominioRepository,
                               final MedioPagoRepository medioPagoRepository,
                               final ComisionRepository comisionRepository,
                               final PerteneceDominioRepository perteneceDominioRepository,
                               final SolicitudTrasladoRepository solicitudTrasladoRepository) {
        this.distribuidorRepository = distribuidorRepository;
        this.tipoDocumentoEmpRepository = tipoDocumentoEmpRepository;
        this.categoriaDistribuidorRepository = categoriaDistribuidorRepository;
        this.usuarioRepository = usuarioRepository;
        this.ticketRepository = ticketRepository;
        this.solicitudDominioRepository = solicitudDominioRepository;
        this.medioPagoRepository = medioPagoRepository;
        this.comisionRepository = comisionRepository;
        this.perteneceDominioRepository = perteneceDominioRepository;
        this.solicitudTrasladoRepository = solicitudTrasladoRepository;
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

    @Transactional
    public ResponseDTO create(DistribuidorRegistroRequestDTO dto) {
        try {
            if (usuarioRepository.findByCorreoUsuario(dto.getCorreoDistrbuidor()) != null) {
                return new ResponseDTO(false, "The email is already registered.");
            }

            TipoDocumentoEmp tipoDoc = tipoDocumentoEmpRepository.findByNombreTipoDoc(dto.getNombreTipoDoc());
            if (tipoDoc == null) {
                return new ResponseDTO(false, "The document type does not exist.");
            }

            Distribuidor distribuidor = new Distribuidor();
            distribuidor.setNumeroDocEmpresa(dto.getNumeroDocEmpresa());
            distribuidor.setNombreEmpresa(dto.getNombreEmpresa());
            distribuidor.setDireccionEmpresa(dto.getDireccionEmpresa());
            distribuidor.setNombreTipoDoc(tipoDoc);
            distribuidorRepository.save(distribuidor);

            Usuario usuario = new Usuario();
            usuario.setCorreoUsuario(dto.getCorreoDistrbuidor());
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasenaDistribuidor()));
            usuario.setRol("Distribuidor");
            usuario.setEstado("PENDIENTE");
            usuario.setDistribuidor(distribuidor);
            usuarioRepository.save(usuario);

            emailService.enviarCorreoSolicitudRegistro(
                    usuario.getCorreoUsuario(),
                    distribuidor.getNombreEmpresa()
            );

            return new ResponseDTO(true, "Distributor successfully created.");
        } catch (Exception e) {
            return new ResponseDTO(false, "Internal error creating the distributor.");
        }
    }


    public void update(final Integer idDistribuidor, final DistribuidorActualizarDTO distribuidorDTO) {
        final Distribuidor distribuidor = distribuidorRepository.findById(idDistribuidor)
                .orElseThrow(NotFoundException::new);

        if (distribuidorDTO.getNumeroDocEmpresa() != null && !distribuidorDTO.getNumeroDocEmpresa().isBlank()) {
            distribuidor.setNumeroDocEmpresa(distribuidorDTO.getNumeroDocEmpresa());
        }

        if (distribuidorDTO.getNombreEmpresa() != null && !distribuidorDTO.getNombreEmpresa().isBlank()) {
            distribuidor.setNombreEmpresa(distribuidorDTO.getNombreEmpresa());
        }

        if (distribuidorDTO.getDireccionEmpresa() != null && !distribuidorDTO.getDireccionEmpresa().isBlank()) {
            distribuidor.setDireccionEmpresa(distribuidorDTO.getDireccionEmpresa());
        }

        if (distribuidorDTO.getNombreTipoDoc() != null && !distribuidorDTO.getNombreTipoDoc().isBlank()) {
            TipoDocumentoEmp tipoDocumento = tipoDocumentoEmpRepository
                    .findByNombreTipoDoc(distribuidorDTO.getNombreTipoDoc());

            if (tipoDocumento == null) {
                throw new IllegalArgumentException("Document type not found: " + distribuidorDTO.getNombreTipoDoc());
            }

            distribuidor.setNombreTipoDoc(tipoDocumento);
        }

        distribuidorRepository.save(distribuidor);
    }

    public List<DistribuidorConCorreoDTO> findAllWithCorreo() {
        List<Distribuidor> distribuidores = distribuidorRepository.findAll(Sort.by("idDistribuidor"));

        return distribuidores.stream().map(distribuidor -> {
            Usuario usuario = usuarioRepository.findFirstByDistribuidor(distribuidor);

            DistribuidorConCorreoDTO dto = new DistribuidorConCorreoDTO();
            dto.setIdDistribuidor(distribuidor.getIdDistribuidor());
            dto.setNumeroDocEmpresa(distribuidor.getNumeroDocEmpresa());
            dto.setNombreEmpresa(distribuidor.getNombreEmpresa());
            dto.setDireccionEmpresa(distribuidor.getDireccionEmpresa());
            dto.setNombreTipoDoc(distribuidor.getNombreTipoDoc() != null ? distribuidor.getNombreTipoDoc().getNombreTipoDoc() : null);
            dto.setCorreo(usuario != null ? usuario.getCorreoUsuario() : null);
            dto.setEstado(usuario != null ? usuario.getEstado() : null);

            return dto;
        }).toList();
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
        distribuidorDTO.setCategoria(distribuidor.getCategoria() == null ? null : distribuidor.getCategoria().getIdCategoria());
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
        final CategoriaDistribuidor categoria = distribuidorDTO.getCategoria() == null ? null : categoriaDistribuidorRepository.findById(distribuidorDTO.getCategoria())
                .orElseThrow(() -> new NotFoundException("categoria not found"));
        distribuidor.setCategoria(categoria);
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
        final Ticket distribuidorTicket = ticketRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorTicket != null) {
            referencedWarning.setKey("distribuidor.ticket.distribuidor.referenced");
            referencedWarning.addParam(distribuidorTicket.getIdTicket());
            return referencedWarning;
        }
        final SolicitudDominio distribuidorSolicitudDominio = solicitudDominioRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorSolicitudDominio != null) {
            referencedWarning.setKey("distribuidor.solicitudDominio.distribuidor.referenced");
            referencedWarning.addParam(distribuidorSolicitudDominio.getIdSolicitud());
            return referencedWarning;
        }
        final MedioPago distribuidorMedioPago = medioPagoRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorMedioPago != null) {
            referencedWarning.setKey("distribuidor.medioPago.distribuidor.referenced");
            referencedWarning.addParam(distribuidorMedioPago.getIdMedioPago());
            return referencedWarning;
        }
        final Comision distribuidorComision = comisionRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorComision != null) {
            referencedWarning.setKey("distribuidor.comision.distribuidor.referenced");
            referencedWarning.addParam(distribuidorComision.getIdComision());
            return referencedWarning;
        }
        final PerteneceDominio distribuidorPerteneceDominio = perteneceDominioRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorPerteneceDominio != null) {
            referencedWarning.setKey("distribuidor.perteneceDominio.distribuidor.referenced");
            referencedWarning.addParam(distribuidorPerteneceDominio.getIdPertenece());
            return referencedWarning;
        }
        final SolicitudTraslado distribuidorSolicitudTraslado = solicitudTrasladoRepository.findFirstByDistribuidor(distribuidor);
        if (distribuidorSolicitudTraslado != null) {
            referencedWarning.setKey("distribuidor.solicitudTraslado.distribuidor.referenced");
            referencedWarning.addParam(distribuidorSolicitudTraslado.getIdSolicitudTraslado());
            return referencedWarning;
        }
        return null;
    }
}