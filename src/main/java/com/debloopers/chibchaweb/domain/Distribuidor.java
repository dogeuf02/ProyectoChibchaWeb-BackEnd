package com.debloopers.chibchaweb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Distribuidor {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer idDistribuidor;

    @Column(nullable = false, length = 20)
    private String numeroDocEmpresa;

    @Column(nullable = false)
    private String nombreEmpresa;

    @Column(nullable = false)
    private String direccionEmpresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nombre_tipo_doc_id", nullable = false)
    private TipoDocumentoEmp nombreTipoDoc;

    @OneToMany(mappedBy = "distribuidor")
    private Set<Usuario> distribuidorUsuarios = new HashSet<>();

    @OneToMany(mappedBy = "distribuidor")
    private Set<SolicitudDomDistribuidor> distribuidorSolicitudDomDistribuidors = new HashSet<>();

    @OneToMany(mappedBy = "distribuidor")
    private Set<Ticket> distribuidorTickets = new HashSet<>();

}
