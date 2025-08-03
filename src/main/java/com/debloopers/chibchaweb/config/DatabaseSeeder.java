package com.debloopers.chibchaweb.config;

import com.debloopers.chibchaweb.dto.AdministradorRegistroRequestDTO;
import com.debloopers.chibchaweb.dto.ResponseDTO;
import com.debloopers.chibchaweb.dto.TipoDocumentoEmpDTO;
import com.debloopers.chibchaweb.dto.TldDTO;
import com.debloopers.chibchaweb.service.AdministradorService;
import com.debloopers.chibchaweb.service.TipoDocumentoEmpService;
import com.debloopers.chibchaweb.service.TldService;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner seedTipoDocumentoEmp(TipoDocumentoEmpService tipoDocumentoEmpService,
            AdministradorService administradorService,
            TldService tldService) {
        return args -> {
            insertarTipo(tipoDocumentoEmpService, "NIT");
            insertarAdministradorPorDefecto(administradorService);
            insertarTld(tldService, ".sapa", BigDecimal.valueOf(500));
        };
    }

    private void insertarTipo(TipoDocumentoEmpService service, String nombreTipoDoc) {
        if (!service.nombreTipoDocExists(nombreTipoDoc)) {
            TipoDocumentoEmpDTO dto = new TipoDocumentoEmpDTO();
            dto.setNombreTipoDoc(nombreTipoDoc);
            service.create(dto);
            System.out.println("🟢 TipoDocumentoEmp insertado: " + nombreTipoDoc);
        }
    }

    private void insertarAdministradorPorDefecto(AdministradorService service) {
        final String correo = "admin@admin.com";
        final String contrasena = "admin@123";

        if (service.findAllWithCorreo().stream().noneMatch(a -> correo.equalsIgnoreCase(a.getCorreo()))) {
            AdministradorRegistroRequestDTO dto = new AdministradorRegistroRequestDTO();
            dto.setNombreAdmin("Admin");
            dto.setApellidoAdmin("Principal");
            dto.setFechaNacimientoAdmin(LocalDate.of(1990, 1, 1));
            dto.setCorreoAdmin(correo);
            dto.setContrasenaAdmin(contrasena);

            ResponseDTO response = service.create(dto);
            if (response.isExito()) {
                System.out.println("🟢 Administrador por defecto creado: " + correo);
            } else {
                System.out.println("🔴 Error al crear admin por defecto: " + response.getMensaje());
            }
        }
    }

    private void insertarTld(TldService service, String tldString, BigDecimal precio) {
    if (!service.tldExists(tldString)) {
        TldDTO dto = new TldDTO();
        dto.setTld(tldString);
        dto.setPrecioTld(precio);
        service.create(dto);
        System.out.println("🟢 TLD insertado: " + tldString);
        }
    }
}