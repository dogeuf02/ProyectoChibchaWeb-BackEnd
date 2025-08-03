package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.BancoDTO;
import com.debloopers.chibchaweb.service.BancoService;
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
@RequestMapping(value = "/api/banco", produces = MediaType.APPLICATION_JSON_VALUE)
public class BancoController {

    private final BancoService bancoService;

    public BancoController(final BancoService bancoService) {
        this.bancoService = bancoService;
    }

    @GetMapping
    public ResponseEntity<List<BancoDTO>> getAllBancos() {
        return ResponseEntity.ok(bancoService.findAll());
    }

    @GetMapping("/{idBanco}")
    public ResponseEntity<BancoDTO> getBanco(
            @PathVariable(name = "idBanco") final Integer idBanco) {
        return ResponseEntity.ok(bancoService.get(idBanco));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createBanco(@RequestBody @Valid final BancoDTO bancoDTO) {
        final Integer createdIdBanco = bancoService.create(bancoDTO);
        return new ResponseEntity<>(createdIdBanco, HttpStatus.CREATED);
    }

    @PutMapping("/{idBanco}")
    public ResponseEntity<Integer> updateBanco(
            @PathVariable(name = "idBanco") final Integer idBanco,
            @RequestBody @Valid final BancoDTO bancoDTO) {
        bancoService.update(idBanco, bancoDTO);
        return ResponseEntity.ok(idBanco);
    }

    @DeleteMapping("/{idBanco}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBanco(@PathVariable(name = "idBanco") final Integer idBanco) {
        final ReferencedWarning referencedWarning = bancoService.getReferencedWarning(idBanco);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        bancoService.delete(idBanco);
        return ResponseEntity.noContent().build();
    }
}