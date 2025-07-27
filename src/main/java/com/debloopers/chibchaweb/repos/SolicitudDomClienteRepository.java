package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.Administrador;
import com.debloopers.chibchaweb.domain.ClienteDirecto;
import com.debloopers.chibchaweb.domain.Dominio;
import com.debloopers.chibchaweb.domain.Registrador;
import com.debloopers.chibchaweb.domain.SolicitudDomCliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SolicitudDomClienteRepository extends JpaRepository<SolicitudDomCliente, String> {

    SolicitudDomCliente findFirstByCliente(ClienteDirecto clienteDirecto);

    SolicitudDomCliente findFirstByNombreDominio(Dominio dominio);

    SolicitudDomCliente findFirstByAdmin(Administrador administrador);

    SolicitudDomCliente findFirstByRegistrador(Registrador registrador);

    boolean existsByTldIgnoreCase(String tld);

}
