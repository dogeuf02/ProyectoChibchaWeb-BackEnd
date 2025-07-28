package com.debloopers.chibchaweb.rest;

import com.debloopers.chibchaweb.model.TldDTO;
import com.debloopers.chibchaweb.service.TldService;
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
@RequestMapping(value = "/api/tld", produces = MediaType.APPLICATION_JSON_VALUE)
public class TldResource {

    private final TldService tldService;

    public TldResource(final TldService tldService) {
        this.tldService = tldService;
    }

    @GetMapping
    public ResponseEntity<List<TldDTO>> getAllTlds() {
        return ResponseEntity.ok(tldService.findAll());
    }

    @GetMapping("/{tld}")
    public ResponseEntity<TldDTO> getTld(@PathVariable(name = "tld") final String tld) {
        return ResponseEntity.ok(tldService.get(tld));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createTld(@RequestBody @Valid final TldDTO tldDTO) {
        final String createdTld = tldService.create(tldDTO);
        return new ResponseEntity<>('"' + createdTld + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{tld}")
    public ResponseEntity<String> updateTld(@PathVariable(name = "tld") final String tld,
            @RequestBody @Valid final TldDTO tldDTO) {
        tldService.update(tld, tldDTO);
        return ResponseEntity.ok('"' + tld + '"');
    }

    @DeleteMapping("/{tld}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTld(@PathVariable(name = "tld") final String tld) {
        final ReferencedWarning referencedWarning = tldService.getReferencedWarning(tld);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        tldService.delete(tld);
        return ResponseEntity.noContent().build();
    }

}
