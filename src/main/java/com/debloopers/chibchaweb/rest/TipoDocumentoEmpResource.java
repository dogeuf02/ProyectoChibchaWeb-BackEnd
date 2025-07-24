package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.TipoDocumentoEmpDTO;
import com.debloopers.chibchaweb.service.TipoDocumentoEmpService;
import com.debloopers.chibchaweb.util.ReferencedException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/tipoDocumentoEmps", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoDocumentoEmpResource {

    private final TipoDocumentoEmpService tipoDocumentoEmpService;

    public TipoDocumentoEmpResource(final TipoDocumentoEmpService tipoDocumentoEmpService) {
        this.tipoDocumentoEmpService = tipoDocumentoEmpService;
    }

    @GetMapping
    public ResponseEntity<List<TipoDocumentoEmpDTO>> getAllTipoDocumentoEmps() {
        return ResponseEntity.ok(tipoDocumentoEmpService.findAll());
    }

    @GetMapping("/{idTipoDocumento}")
    public ResponseEntity<TipoDocumentoEmpDTO> getTipoDocumentoEmp(
            @PathVariable(name = "idTipoDocumento") final Integer idTipoDocumento) {
        return ResponseEntity.ok(tipoDocumentoEmpService.get(idTipoDocumento));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTipoDocumentoEmp(
            @RequestBody @Valid final TipoDocumentoEmpDTO tipoDocumentoEmpDTO) {
        final Integer createdIdTipoDocumento = tipoDocumentoEmpService.create(tipoDocumentoEmpDTO);
        return new ResponseEntity<>(createdIdTipoDocumento, HttpStatus.CREATED);
    }

    @PutMapping("/{idTipoDocumento}")
    public ResponseEntity<Integer> updateTipoDocumentoEmp(
            @PathVariable(name = "idTipoDocumento") final Integer idTipoDocumento,
            @RequestBody @Valid final TipoDocumentoEmpDTO tipoDocumentoEmpDTO) {
        tipoDocumentoEmpService.update(idTipoDocumento, tipoDocumentoEmpDTO);
        return ResponseEntity.ok(idTipoDocumento);
    }

    @DeleteMapping("/{idTipoDocumento}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTipoDocumentoEmp(
            @PathVariable(name = "idTipoDocumento") final Integer idTipoDocumento) {
        final ReferencedWarning referencedWarning = tipoDocumentoEmpService.getReferencedWarning(idTipoDocumento);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        tipoDocumentoEmpService.delete(idTipoDocumento);
        return ResponseEntity.noContent().build();
    }

}
