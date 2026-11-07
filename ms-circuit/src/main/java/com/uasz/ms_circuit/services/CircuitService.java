package com.uasz.ms_circuit.services;

import com.uasz.ms_circuit.entities.Circuit;
import com.uasz.ms_circuit.entities.Etape;
import com.uasz.ms_circuit.repositories.CircuitRepository;
import com.uasz.ms_circuit.repositories.EtapeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CircuitService {

    private final CircuitRepository circuitRepository;
    private final EtapeRepository etapeRepository;

    public CircuitService(CircuitRepository circuitRepository, EtapeRepository etapeRepository) {
        this.circuitRepository = circuitRepository;
        this.etapeRepository = etapeRepository;
    }

    public Circuit saveCircuit(Circuit c) { return circuitRepository.save(c); }
    public List<Circuit> getAllCircuits() { return circuitRepository.findAll(); }
    public Optional<Circuit> getCircuitById(Long id) { return circuitRepository.findById(id); }

    public Circuit updateCircuit(Long id, Circuit n) {
        return circuitRepository.findById(id).map(c -> {
            c.setCodeCircuit(n.getCodeCircuit());
            c.setNomCircuit(n.getNomCircuit());
            c.setDescriptionCircuit(n.getDescriptionCircuit());
            c.setDureeTotale(n.getDureeTotale());
            c.setDistanceTotale(n.getDistanceTotale());
            c.setDifficulte(n.getDifficulte());
            return circuitRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Circuit non trouvé : " + id));
    }

    public void deleteCircuit(Long id) {
        etapeRepository.deleteAll(etapeRepository.findByCircuitIdOrderByPosition(id));
        circuitRepository.deleteById(id);
    }

    // Étapes
    public Etape addEtape(Long circuitId, Etape e) {
        Circuit c = circuitRepository.findById(circuitId)
                .orElseThrow(() -> new RuntimeException("Circuit non trouvé : " + circuitId));
        e.setCircuit(c);
        return etapeRepository.save(e);
    }

    public List<Etape> getEtapes(Long circuitId) {
        return etapeRepository.findByCircuitIdOrderByPosition(circuitId);
    }

    public void deleteEtape(Long etapeId) { etapeRepository.deleteById(etapeId); }
}