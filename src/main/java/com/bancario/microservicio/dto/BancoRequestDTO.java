package com.bancario.microservicio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class BancoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 255, message = "El nombre es demasiado largo")
    private String nombre;

    @NotBlank(message = "El código BIC es obligatorio")
    @Size(min = 8, max = 11, message = "El código BIC debe tener entre 8 y 11 caracteres")
    private String codigoBic;

    @NotNull(message = "La fecha de fundación es obligatoria")
    private LocalDate fechaFundacion;

    @NotNull(message = "El capital social es obligatorio")
    private BigDecimal capitalSocial;


}