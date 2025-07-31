package com.debloopers.chibchaweb.controller;

import com.debloopers.chibchaweb.dto.TokenVerificacionDTO;
import com.debloopers.chibchaweb.service.TokenVerificacionService;
import com.debloopers.chibchaweb.util.ReferencedException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/token-verificacion", produces = MediaType.APPLICATION_JSON_VALUE)
public class TokenVerificacionController {

    private final TokenVerificacionService tokenVerificacionService;

    public TokenVerificacionController(final TokenVerificacionService tokenVerificacionService) {
        this.tokenVerificacionService = tokenVerificacionService;
    }

    @GetMapping
    public ResponseEntity<List<TokenVerificacionDTO>> getAllTokens() {
        return ResponseEntity.ok(tokenVerificacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TokenVerificacionDTO> getToken(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(tokenVerificacionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createToken(@RequestBody @Valid final TokenVerificacionDTO tokenVerificacionDTO) {
        final Long createdId = tokenVerificacionService.create(tokenVerificacionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateToken(@PathVariable(name = "id") final Long id,
                                            @RequestBody @Valid final TokenVerificacionDTO tokenVerificacionDTO) {
        tokenVerificacionService.update(id, tokenVerificacionDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteToken(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = tokenVerificacionService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        tokenVerificacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}