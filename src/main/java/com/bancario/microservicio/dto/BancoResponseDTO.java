package com.bancario.microservicio.dto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Builder
@Data
public class BancoResponseDTO {

    private Long id;

    private String nombre;

    private String codigoBic;

    private LocalDate fechaFundacion;

    private BigDecimal capitalSocial;

}