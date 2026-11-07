package com.uasz.ms_reservation.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeReservation;   // CIRCUIT ou EVENEMENT
    private Long refId;               // id du circuit ou de l'événement
    private String nomClient;
    private String emailClient;
    private int nombrePersonnes;
    private String statut;            // EN_ATTENTE, CONFIRMEE, ANNULEE
    private LocalDateTime dateReservation;
}