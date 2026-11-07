package com.uasz.ms_reservation.repositories;

import com.uasz.ms_reservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}