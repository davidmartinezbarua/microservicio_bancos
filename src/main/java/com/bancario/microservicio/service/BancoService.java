package com.bancario.microservicio.service;

import com.bancario.microservicio.dto.BancoRequestDTO;
import com.bancario.microservicio.dto.BancoResponseDTO;
import com.bancario.microservicio.exception.BancoDuplicadoException;
import com.bancario.microservicio.exception.BancoNoEncontradoException;
import com.bancario.microservicio.model.Banco;
import com.bancario.microservicio.repository.BancoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class BancoService {

    private final BancoRepository bancoRepository;

    public BancoService(BancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    @Transactional
    public Banco crearBanco(Banco banco) {
        log.info("Iniciando la creación del banco con nombre: {}", banco.getNombre());

        try {

        if (bancoRepository.findByNombre(banco.getNombre()).isPresent()) {
            throw new BancoDuplicadoException("Ya existe un banco con el nombre: " + banco.getNombre());
        }

        if (bancoRepository.findByCodigoBic(banco.getCodigoBic()).isPresent()) {
            throw new BancoDuplicadoException("Ya existe un banco con el código BIC: " + banco.getCodigoBic());
        }

        return bancoRepository.save(banco);

        } catch (Exception e) {
            log.error("Error al guardar el banco: {}", e.getMessage(), e);
            throw new BancoDuplicadoException("El banco ya existe.");
        }
    }

    @Transactional(readOnly = true)
    public Page<Banco> obtenerTodosPaginado(Pageable pageable) {
        return bancoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Banco obtenerPorId(Long id) {
        log.debug("Buscando banco con ID: {}", id);
        return bancoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Banco no encontrado con ID: {}", id);
                    return new EntityNotFoundException("Banco no encontrado con ID: " + id);
                });
    }

    @Transactional
    public Banco actualizarBanco(Long id, Banco detallesBanco) {
        Banco bancoExistente = obtenerPorId(id);

        bancoExistente.setNombre(detallesBanco.getNombre());
        bancoExistente.setCodigoBic(detallesBanco.getCodigoBic());
        bancoExistente.setCapitalSocial(detallesBanco.getCapitalSocial());
        bancoExistente.setFechaFundacion(detallesBanco.getFechaFundacion());

        return bancoRepository.save(bancoExistente);
    }


    @Transactional
    public void eliminarBanco(Long id) {
        //  404 si no existe
        Banco banco = obtenerPorId(id);

        bancoRepository.delete(banco);
    }

    public Banco toEntity(BancoRequestDTO dto) {

        return Banco.builder()
                .nombre(dto.getNombre())
                .codigoBic(dto.getCodigoBic())
                .fechaFundacion(dto.getFechaFundacion())
                .capitalSocial(dto.getCapitalSocial())
                .build();
    }

    public BancoResponseDTO toResponseDTO(Banco banco) {

        return BancoResponseDTO.builder()
                .id(banco.getId())
                .nombre(banco.getNombre())
                .capitalSocial(banco.getCapitalSocial())
                .codigoBic(banco.getCodigoBic())
                .build();
    }
}