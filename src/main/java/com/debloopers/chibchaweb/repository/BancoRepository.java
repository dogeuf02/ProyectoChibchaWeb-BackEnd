package com.debloopers.chibchaweb.repository;

import com.debloopers.chibchaweb.entity.Banco;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BancoRepository extends JpaRepository<Banco, Integer> {
}