package com.uasz.ms_circuit.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Circuit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeCircuit;
    private String nomCircuit;
    @Column(length = 2000)
    private String descriptionCircuit;
    private int dureeTotale;      // en minutes
    private int distanceTotale;   // en km
    private String difficulte;    // FACILE, MOYEN, DIFFICILE
}