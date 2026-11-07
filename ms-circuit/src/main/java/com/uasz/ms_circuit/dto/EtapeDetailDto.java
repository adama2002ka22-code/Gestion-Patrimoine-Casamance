package com.uasz.ms_circuit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtapeDetailDto {
    private Long id;
    private int position;
    private int dureeEstime;
    private PatrimoineDto patrimoine;
}