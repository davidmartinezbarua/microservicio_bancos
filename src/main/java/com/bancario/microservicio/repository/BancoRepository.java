package com.bancario.microservicio.repository;

import com.bancario.microservicio.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {

    // Genera automáticamente SELECT * FROM bancos WHERE nombre = ?
    Optional<Banco> findByNombre(String nombre);

    // Genera automáticamente SELECT * FROM bancos WHERE codigo_bic = ?
    Optional<Banco> findByCodigoBic(String codigoBic);
}