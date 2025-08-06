package com.debloopers.chibchaweb.service;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.debloopers.chibchaweb.dto.*;
import com.debloopers.chibchaweb.entity.*;
import com.debloopers.chibchaweb.repository.*;
import com.debloopers.chibchaweb.util.NotFoundException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SolicitudDominioService {

    private final SolicitudDominioRepository solicitudDominioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteDirectoRepository clienteDirectoRepository;
    private final DistribuidorRepository distribuidorRepository;
    private final DominioRepository dominioRepository;
    private final AdministradorRepository administradorRepository;

    public SolicitudDominioService(final SolicitudDominioRepository solicitudDominioRepository,
                                   final UsuarioRepository usuarioRepository,
                                   final ClienteDirectoRepository clienteDirectoRepository,
                                   final DistribuidorRepository distribuidorRepository,
                                   final DominioRepository dominioRepository,
                                   final AdministradorRepository administradorRepository) {
        this.solicitudDominioRepository = solicitudDominioRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteDirectoRepository = clienteDirectoRepository;
        this.distribuidorRepository = distribuidorRepository;
        this.dominioRepository = dominioRepository;
        this.administradorRepository = administradorRepository;
    }

    public List<SolicitudDominioDTO> findAll() {
        final List<SolicitudDominio> solicitudDominios = solicitudDominioRepository.findAll(Sort.by("idSolicitud"));
        return solicitudDominios.stream()
                .map(solicitudDominio -> mapToDTO(solicitudDominio, new SolicitudDominioDTO()))
                .toList();
    }

    public SolicitudDominioDTO get(final Integer idSolicitud) {
        return solicitudDominioRepository.findById(idSolicitud)
                .map(solicitudDominio -> mapToDTO(solicitudDominio, new SolicitudDominioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public List<SolicitudDominioDTO> obtenerSolicitudesPorCliente(Integer idCliente) {
        List<SolicitudDominio> solicitudes = solicitudDominioRepository.findByCliente_IdCliente(idCliente);
        return solicitudes.stream()
                .map(s -> mapToDTO(s, new SolicitudDominioDTO()))
                .toList();
    }

    @Transactional
    public List<SolicitudDominioDTO> obtenerSolicitudesPorDistribuidor(Integer idDistribuidor) {
        List<SolicitudDominio> solicitudes = solicitudDominioRepository.findByDistribuidor_IdDistribuidor(idDistribuidor);
        return solicitudes.stream()
                .map(s -> mapToDTO(s, new SolicitudDominioDTO()))
                .toList();
    }

    @Transactional
    public ResponseDTO create(final SolicitudDominioDTO solicitudDominioDTO) {

        final Dominio dominio = dominioRepository.findById(solicitudDominioDTO.getDominio())
                .orElse(null);

        if (dominio == null) {
            return new ResponseDTO(false, "The specified domain does not exist.");
        }

        String estadoDominio = dominio.getEstado();
        if ("En uso".equalsIgnoreCase(estadoDominio)) {
            return new ResponseDTO(false, "The domain is already in use or is reserved.");
        }

        if (solicitudDominioDTO.getCliente() != null && solicitudDominioDTO.getDistribuidor() != null) {
            return new ResponseDTO(false, "Only specify customer or distributor, not both.");
        }

        if (solicitudDominioDTO.getCliente() == null && solicitudDominioDTO.getDistribuidor() == null) {
            return new ResponseDTO(false, "You must specify a customer or distributor.");
        }

        boolean solicitudEnRevisionExiste = false;

        if (solicitudDominioDTO.getCliente() != null) {
            solicitudEnRevisionExiste = solicitudDominioRepository.existsEnRevisionByCliente(
                    solicitudDominioDTO.getCliente(), solicitudDominioDTO.getDominio());
        } else if (solicitudDominioDTO.getDistribuidor() != null) {
            solicitudEnRevisionExiste = solicitudDominioRepository.existsEnRevisionByDistribuidor(
                    solicitudDominioDTO.getDistribuidor(), solicitudDominioDTO.getDominio());
        }

        if (solicitudEnRevisionExiste) {
            return new ResponseDTO(false, "There is already a pending request for this domain by this customer or distributor.");
        }

        final SolicitudDominio solicitudDominio = new SolicitudDominio();
        mapToEntity(solicitudDominioDTO, solicitudDominio);
        solicitudDominioRepository.save(solicitudDominio);

        return new ResponseDTO(true, "Request successfully created.");
    }

    @Transactional
    public File generarXMLSolicitudDominio(Integer idSolicitud) {
        SolicitudDominio solicitud = solicitudDominioRepository.findById(idSolicitud)
                .orElseThrow(() -> new NotFoundException("Request not found"));

        SolicitudXML solicitudXML = new SolicitudXML();
        solicitudXML.setOrigen("Chibchaweb");
        solicitudXML.setFechaSolicitud(solicitud.getFechaSolicitud().format(DateTimeFormatter.ISO_DATE));

        if (solicitud.getCliente() != null) {
            ClienteDirecto c = solicitud.getCliente();
            ClienteXML cxml = new ClienteXML();
            cxml.setNombre(c.getNombreCliente());
            cxml.setApellido(c.getApellidoCliente());
            cxml.setTelefono(c.getTelefono());
            cxml.setFechaNacimiento(
                    c.getFechaNacimientoCliente() != null ? c.getFechaNacimientoCliente().toString() : null
            );

            Optional<Usuario> usuario = usuarioRepository.findByCliente_IdCliente(c.getIdCliente());
            usuario.ifPresent(u -> cxml.setCorreo(u.getCorreoUsuario()));

            solicitudXML.setCliente(cxml);
        }

        if (solicitud.getDistribuidor() != null) {
            Distribuidor d = solicitud.getDistribuidor();
            DistribuidorXML dxml = new DistribuidorXML();
            dxml.setNombreEmpresa(d.getNombreEmpresa());
            dxml.setNumeroDocumento(d.getNumeroDocEmpresa());
            dxml.setDireccionEmpresa(d.getDireccionEmpresa());

            Optional<Usuario> usuario = usuarioRepository.findByDistribuidor_IdDistribuidor(d.getIdDistribuidor());
            usuario.ifPresent(u -> dxml.setCorreo(u.getCorreoUsuario()));

            solicitudXML.setDistribuidor(dxml);
        }

        Dominio dominio = solicitud.getDominio();
        DominioXML dominioXML = new DominioXML();
        dominioXML.setNombreCompleto(dominio.getNombreDominio() + dominio.getTld().getTld());
        dominioXML.setEstado(dominio.getEstado());
        dominioXML.setIdTld(dominio.getTld().getTld());

        solicitudXML.setDominio(String.valueOf(dominioXML));

        try {
            JAXBContext context = JAXBContext.newInstance(SolicitudXML.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File archivoXML = new File("Request_" + idSolicitud + ".xml");
            marshaller.marshal(solicitudXML, archivoXML);
            return archivoXML;

        } catch (Exception e) {
            throw new RuntimeException("Error generating XML: " + e.getMessage());
        }
    }

    public void update(final Integer idSolicitud, final SolicitudDominioDTO solicitudDominioDTO) {
        final SolicitudDominio solicitudDominio = solicitudDominioRepository.findById(idSolicitud)
                .orElseThrow(NotFoundException::new);
        mapToEntity(solicitudDominioDTO, solicitudDominio);
        solicitudDominioRepository.save(solicitudDominio);
    }

    public void delete(final Integer idSolicitud) {
        solicitudDominioRepository.deleteById(idSolicitud);
    }

    private SolicitudDominioDTO mapToDTO(final SolicitudDominio solicitudDominio,
                                         final SolicitudDominioDTO solicitudDominioDTO) {
        solicitudDominioDTO.setIdSolicitud(solicitudDominio.getIdSolicitud());
        solicitudDominioDTO.setEstadoSolicitud(solicitudDominio.getEstadoSolicitud());
        solicitudDominioDTO.setFechaSolicitud(solicitudDominio.getFechaSolicitud());
        solicitudDominioDTO.setFechaAprobacion(solicitudDominio.getFechaAprobacion());
        solicitudDominioDTO.setCliente(solicitudDominio.getCliente() == null ? null : solicitudDominio.getCliente().getIdCliente());
        solicitudDominioDTO.setDistribuidor(solicitudDominio.getDistribuidor() == null ? null : solicitudDominio.getDistribuidor().getIdDistribuidor());
        solicitudDominioDTO.setDominio(solicitudDominio.getDominio() == null ? null : solicitudDominio.getDominio().getIdDominio());
        solicitudDominioDTO.setAdmin(solicitudDominio.getAdmin() == null ? null : solicitudDominio.getAdmin().getIdAdmin());
        return solicitudDominioDTO;
    }

    private SolicitudDominio mapToEntity(final SolicitudDominioDTO solicitudDominioDTO,
                                         final SolicitudDominio solicitudDominio) {
        solicitudDominio.setEstadoSolicitud(solicitudDominioDTO.getEstadoSolicitud());
        solicitudDominio.setFechaSolicitud(solicitudDominioDTO.getFechaSolicitud());
        solicitudDominio.setFechaAprobacion(solicitudDominioDTO.getFechaAprobacion());
        final ClienteDirecto cliente = solicitudDominioDTO.getCliente() == null ? null : clienteDirectoRepository.findById(solicitudDominioDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        solicitudDominio.setCliente(cliente);
        final Distribuidor distribuidor = solicitudDominioDTO.getDistribuidor() == null ? null : distribuidorRepository.findById(solicitudDominioDTO.getDistribuidor())
                .orElseThrow(() -> new NotFoundException("distribuidor not found"));
        solicitudDominio.setDistribuidor(distribuidor);
        final Dominio dominio = solicitudDominioDTO.getDominio() == null ? null : dominioRepository.findById(solicitudDominioDTO.getDominio())
                .orElseThrow(() -> new NotFoundException("dominio not found"));
        solicitudDominio.setDominio(dominio);
        final Administrador admin = solicitudDominioDTO.getAdmin() == null ? null : administradorRepository.findById(solicitudDominioDTO.getAdmin())
                .orElseThrow(() -> new NotFoundException("admin not found"));
        solicitudDominio.setAdmin(admin);
        return solicitudDominio;
    }
}