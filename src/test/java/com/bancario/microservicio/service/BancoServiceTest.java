package com.bancario.microservicio.service;

import com.bancario.microservicio.exception.BancoDuplicadoException;
import com.bancario.microservicio.model.Banco;
import com.bancario.microservicio.repository.BancoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BancoServiceTest {

    @Mock
    private BancoRepository bancoRepository;

    @InjectMocks
    private BancoService bancoService;

    private Banco bancoDuplicado;

    @BeforeEach
    void setUp() {
        bancoDuplicado = Banco.builder().id(34234L).codigoBic("BICR654654").nombre("Test Bank").fechaFundacion(LocalDate.of(1983, 12, 4)).build();
    }

    @Test
    void crearBanco_DeberiaGuardarExitosamente() {
        when(bancoRepository.findByNombre(anyString())).thenReturn(Optional.empty());
        when(bancoRepository.findByCodigoBic(anyString())).thenReturn(Optional.empty());

        when(bancoRepository.save(any(Banco.class))).thenReturn(bancoDuplicado);

        Banco resultado = bancoService.crearBanco(bancoDuplicado);

        assertNotNull(resultado);
        assertEquals("Banco de Pruebas", resultado.getNombre());
        verify(bancoRepository, times(1)).save(bancoDuplicado);
    }

    @Test
    void crearBanco_DeberiaLanzarExcepcionPorNombreDuplicado() {

        when(bancoRepository.findByNombre(bancoDuplicado.getNombre()))
                .thenReturn(Optional.of(bancoDuplicado));
        assertThrows(BancoDuplicadoException.class, () -> {
            bancoService.crearBanco(bancoDuplicado);
        });
        verify(bancoRepository, never()).save(any(Banco.class));
    }
}