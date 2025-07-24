package com.debloopers.chibchaweb.repos;

import com.debloopers.chibchaweb.domain.Administrador;
import com.debloopers.chibchaweb.domain.ClienteDirecto;
import com.debloopers.chibchaweb.domain.Dominio;
import com.debloopers.chibchaweb.domain.Registrador;
import com.debloopers.chibchaweb.domain.SolicitudDomCd;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SolicitudDomCdRepository extends JpaRepository<SolicitudDomCd, String> {

    SolicitudDomCd findFirstByRegistrador(Registrador registrador);

    SolicitudDomCd findFirstByNombreDominio(Dominio dominio);

    SolicitudDomCd findFirstByCliente(ClienteDirecto clienteDirecto);

    SolicitudDomCd findFirstByAdmin(Administrador administrador);

    boolean existsByTldIgnoreCase(String tld);

}
