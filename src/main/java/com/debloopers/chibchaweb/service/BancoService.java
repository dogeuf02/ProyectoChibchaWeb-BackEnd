package com.debloopers.chibchaweb.service;


import java.util.List;

import com.debloopers.chibchaweb.dto.BancoDTO;
import com.debloopers.chibchaweb.entity.Banco;
import com.debloopers.chibchaweb.entity.MedioPago;
import com.debloopers.chibchaweb.repository.BancoRepository;
import com.debloopers.chibchaweb.repository.MedioPagoRepository;
import com.debloopers.chibchaweb.util.NotFoundException;
import com.debloopers.chibchaweb.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BancoService {

    private final BancoRepository bancoRepository;
    private final MedioPagoRepository medioPagoRepository;

    public BancoService(final BancoRepository bancoRepository,
            final MedioPagoRepository medioPagoRepository) {
        this.bancoRepository = bancoRepository;
        this.medioPagoRepository = medioPagoRepository;
    }

    public List<BancoDTO> findAll() {
        final List<Banco> bancoes = bancoRepository.findAll(Sort.by("idBanco"));
        return bancoes.stream()
                .map(banco -> mapToDTO(banco, new BancoDTO()))
                .toList();
    }

    public BancoDTO get(final Integer idBanco) {
        return bancoRepository.findById(idBanco)
                .map(banco -> mapToDTO(banco, new BancoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final BancoDTO bancoDTO) {
        final Banco banco = new Banco();
        mapToEntity(bancoDTO, banco);
        return bancoRepository.save(banco).getIdBanco();
    }

    public void update(final Integer idBanco, final BancoDTO bancoDTO) {
        final Banco banco = bancoRepository.findById(idBanco)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bancoDTO, banco);
        bancoRepository.save(banco);
    }

    public void delete(final Integer idBanco) {
        bancoRepository.deleteById(idBanco);
    }

    private BancoDTO mapToDTO(final Banco banco, final BancoDTO bancoDTO) {
        bancoDTO.setIdBanco(banco.getIdBanco());
        bancoDTO.setNombreBanco(banco.getNombreBanco());
        return bancoDTO;
    }

    private Banco mapToEntity(final BancoDTO bancoDTO, final Banco banco) {
        banco.setNombreBanco(bancoDTO.getNombreBanco());
        return banco;
    }

    public ReferencedWarning getReferencedWarning(final Integer idBanco) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Banco banco = bancoRepository.findById(idBanco)
                .orElseThrow(NotFoundException::new);
        final MedioPago bancoMedioPago = medioPagoRepository.findFirstByBanco(banco);
        if (bancoMedioPago != null) {
            referencedWarning.setKey("banco.medioPago.banco.referenced");
            referencedWarning.addParam(bancoMedioPago.getIdMedioPago());
            return referencedWarning;
        }
        return null;
    }
}