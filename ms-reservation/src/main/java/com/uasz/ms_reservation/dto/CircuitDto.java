package com.uasz.ms_reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CircuitDto {
    private Long id;
    private String codeCircuit;
    private String nomCircuit;
    private String descriptionCircuit;
    private int dureeTotale;
    private int distanceTotale;
}
