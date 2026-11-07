package com.uasz.ms_reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvenementDto {
    private Long id;
    private String titre;
    private Integer placesMax;
    private Integer placesRestantes;
}