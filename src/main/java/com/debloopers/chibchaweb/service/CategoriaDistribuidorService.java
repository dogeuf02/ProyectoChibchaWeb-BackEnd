package com.debloopers.chibchaweb.service;


import com.debloopers.chibchaweb.dto.CategoriaDistribuidorDTO;
import com.debloopers.chibchaweb.entity.CategoriaDistribuidor;
import com.debloopers.chibchaweb.repository.CategoriaDistribuidorRepository;
import com.debloopers.chibchaweb.util.NotFoundException;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoriaDistribuidorService {

    private final CategoriaDistribuidorRepository categoriaDistribuidorRepository;

    public CategoriaDistribuidorService(
            final CategoriaDistribuidorRepository categoriaDistribuidorRepository) {
        this.categoriaDistribuidorRepository = categoriaDistribuidorRepository;
    }

    public List<CategoriaDistribuidorDTO> findAll() {
        final List<CategoriaDistribuidor> categoriaDistribuidors = categoriaDistribuidorRepository.findAll(Sort.by("idCategoria"));
        return categoriaDistribuidors.stream()
                .map(categoriaDistribuidor -> mapToDTO(categoriaDistribuidor, new CategoriaDistribuidorDTO()))
                .toList();
    }

    public CategoriaDistribuidorDTO get(final Integer idCategoria) {
        return categoriaDistribuidorRepository.findById(idCategoria)
                .map(categoriaDistribuidor -> mapToDTO(categoriaDistribuidor, new CategoriaDistribuidorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CategoriaDistribuidorDTO categoriaDistribuidorDTO) {
        final CategoriaDistribuidor categoriaDistribuidor = new CategoriaDistribuidor();
        mapToEntity(categoriaDistribuidorDTO, categoriaDistribuidor);
        return categoriaDistribuidorRepository.save(categoriaDistribuidor).getIdCategoria();
    }

    public void update(final Integer idCategoria,
            final CategoriaDistribuidorDTO categoriaDistribuidorDTO) {
        final CategoriaDistribuidor categoriaDistribuidor = categoriaDistribuidorRepository.findById(idCategoria)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoriaDistribuidorDTO, categoriaDistribuidor);
        categoriaDistribuidorRepository.save(categoriaDistribuidor);
    }

    public void delete(final Integer idCategoria) {
        categoriaDistribuidorRepository.deleteById(idCategoria);
    }

    private CategoriaDistribuidorDTO mapToDTO(final CategoriaDistribuidor categoriaDistribuidor,
            final CategoriaDistribuidorDTO categoriaDistribuidorDTO) {
        categoriaDistribuidorDTO.setIdCategoria(categoriaDistribuidor.getIdCategoria());
        categoriaDistribuidorDTO.setNombreCategoria(categoriaDistribuidor.getNombreCategoria());
        categoriaDistribuidorDTO.setPrecioCategoria(categoriaDistribuidor.getPrecioCategoria());
        return categoriaDistribuidorDTO;
    }

    private CategoriaDistribuidor mapToEntity(
            final CategoriaDistribuidorDTO categoriaDistribuidorDTO,
            final CategoriaDistribuidor categoriaDistribuidor) {
        categoriaDistribuidor.setNombreCategoria(categoriaDistribuidorDTO.getNombreCategoria());
        categoriaDistribuidor.setPrecioCategoria(categoriaDistribuidorDTO.getPrecioCategoria());
        return categoriaDistribuidor;
    }
}