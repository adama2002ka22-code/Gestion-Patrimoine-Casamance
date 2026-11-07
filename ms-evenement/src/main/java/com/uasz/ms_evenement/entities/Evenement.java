package com.uasz.ms_evenement.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String type;             // EXPOSITION, ATELIER, VISITE_GUIDEE
    @Column(length = 2000)
    private String description;
    private LocalDate dateEvenement;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String lieu;
    private Integer placesMax;
    private Integer placesRestantes;
    private Long patrimoineId;
}