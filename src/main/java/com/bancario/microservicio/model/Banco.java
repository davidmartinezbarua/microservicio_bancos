package com.bancario.microservicio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bancos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Columna única: clave de negocio crucial para validar duplicidad
    @Column(nullable = false, unique = true)
    private String nombre;

    // Columna única: clave internacional para validar duplicidad
    @Column(nullable = false, unique = true, length = 11)
    private String codigoBic;

    private LocalDate fechaFundacion;

    @Column(precision = 19, scale = 2)
    private BigDecimal capitalSocial;

}