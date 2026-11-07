package com.uasz.ms_circuit.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Etape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int position;
    private int dureeEstime;

    @ManyToOne
    @JoinColumn(name = "circuit_id")
    private Circuit circuit;

    private Long patrimoineId;   // simple identifiant, pas de DTO ici
}