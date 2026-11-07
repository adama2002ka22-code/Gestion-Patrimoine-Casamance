package com.uasz.ms_patrimoine.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Patrimoine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomPatrimoine;
    private String codePatrimoine;
    @Column(length = 2000)
    private String descriptionPatrimoine;
    @Column(length = 2000)
    private String historiquePatrimoine;
    private String adressePatrimoine;
    private int capacitePatrimoine;
    private float latitudePatrimoine;
    private float longitudePatrimoine;
}