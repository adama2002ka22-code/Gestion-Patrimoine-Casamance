package com.uasz.ms_evenement.repositories;

import com.uasz.ms_evenement.entities.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {
}