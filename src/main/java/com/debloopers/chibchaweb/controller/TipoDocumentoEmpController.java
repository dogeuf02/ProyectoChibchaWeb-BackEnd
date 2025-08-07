package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.TipoDocumentoEmpDTO;
import com.debloopers.chibchaweb.service.TipoDocumentoEmpService;
import com.debloopers.chibchaweb.util.ReferencedException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/tipoDocumentoEmp", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoDocumentoEmpController {

    private final TipoDocumentoEmpService tipoDocumentoEmpService;

    public TipoDocumentoEmpController(final TipoDocumentoEmpService tipoDocumentoEmpService) {
        this.tipoDocumentoEmpService = tipoDocumentoEmpService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<TipoDocumentoEmpDTO>> getAllTipoDocumentoEmps() {
        return ResponseEntity.ok(tipoDocumentoEmpService.findAll());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{nombreTipoDoc}")
    public ResponseEntity<TipoDocumentoEmpDTO> getTipoDocumentoEmp(
            @PathVariable(name = "nombreTipoDoc") final String nombreTipoDoc) {
        return ResponseEntity.ok(tipoDocumentoEmpService.get(nombreTipoDoc));
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createTipoDocumentoEmp(
            @RequestBody @Valid final TipoDocumentoEmpDTO tipoDocumentoEmpDTO) {
        final String createdNombreTipoDoc = tipoDocumentoEmpService.create(tipoDocumentoEmpDTO);
        return new ResponseEntity<>('"' + createdNombreTipoDoc + '"', HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @PutMapping("/{nombreTipoDoc}")
    public ResponseEntity<String> updateTipoDocumentoEmp(
            @PathVariable(name = "nombreTipoDoc") final String nombreTipoDoc,
            @RequestBody @Valid final TipoDocumentoEmpDTO tipoDocumentoEmpDTO) {
        tipoDocumentoEmpService.update(nombreTipoDoc, tipoDocumentoEmpDTO);
        return ResponseEntity.ok('"' + nombreTipoDoc + '"');
    }

    @PreAuthorize("hasAuthority('Administrador')")
    @DeleteMapping("/{nombreTipoDoc}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTipoDocumentoEmp(
            @PathVariable(name = "nombreTipoDoc") final String nombreTipoDoc) {
        final ReferencedWarning referencedWarning = tipoDocumentoEmpService.getReferencedWarning(nombreTipoDoc);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        tipoDocumentoEmpService.delete(nombreTipoDoc);
        return ResponseEntity.noContent().build();
    }
}