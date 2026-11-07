package com.uasz.ms_circuit.repositories;

import com.uasz.ms_circuit.entities.Etape;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EtapeRepository extends JpaRepository<Etape, Long> {
    List<Etape> findByCircuitIdOrderByPosition(Long circuitId);
}