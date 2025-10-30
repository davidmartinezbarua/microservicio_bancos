package com.bancario.microservicio.controller;

import com.bancario.microservicio.dto.ErrorResponseDTO;
import com.bancario.microservicio.model.Banco;
import com.bancario.microservicio.service.BancoService;
import com.bancario.microservicio.dto.BancoRequestDTO;
import com.bancario.microservicio.dto.BancoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/api/v1/bancos")
@Tag(name = "Servicio Bancos", description = "CRUD de bancos")
public class BancoController {

    private final BancoService bancoService;
    private final RestTemplate restTemplate;

    public BancoController(BancoService bancoService, RestTemplate restTemplate) {
        this.bancoService = bancoService;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    @Operation(summary = "Crear Banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Banco creado con éxito."),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (ej. error de validación del DTO).",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto: El Nombre o Código BIC ya existe.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<BancoResponseDTO> crearBanco(@RequestBody BancoRequestDTO requestDTO) {

        Banco nuevoBanco = bancoService.toEntity(requestDTO);
        Banco bancoGuardado = bancoService.crearBanco(nuevoBanco);
        BancoResponseDTO responseDTO = bancoService.toResponseDTO(bancoGuardado);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener lista de Bancos paginada")
    public ResponseEntity<Page<BancoResponseDTO>> obtenerTodos(
            @Parameter(description = "Parámetros de paginación: page, size, y sort.")
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        Page<Banco> bancoPage = bancoService.obtenerTodosPaginado(pageable);

        Page<BancoResponseDTO> responseDTOPage = bancoPage.map(bancoService::toResponseDTO);

        return ResponseEntity.ok(responseDTOPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get Banco by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Banco encontrado."),
            @ApiResponse(responseCode = "404", description = "Banco no encontrado.",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<BancoResponseDTO> obtenerPorId(@PathVariable Long id) {
        Banco banco = bancoService.obtenerPorId(id);

        BancoResponseDTO responseDTO = bancoService.toResponseDTO(banco);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BancoResponseDTO> actualizarBanco(@PathVariable Long id, @RequestBody BancoRequestDTO requestDTO) {
        Banco detallesBanco = bancoService.toEntity(requestDTO);
        Banco bancoActualizado = bancoService.actualizarBanco(id, detallesBanco);
        BancoResponseDTO responseDTO = bancoService.toResponseDTO(bancoActualizado);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBanco(@PathVariable Long id) {
        bancoService.eliminarBanco(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/autoconsumo")
    public ResponseEntity<List> obtenerBancosPorAutoconsumo() {
        String url = "http://localhost:8080/api/v1/bancos";

        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);

        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }
}