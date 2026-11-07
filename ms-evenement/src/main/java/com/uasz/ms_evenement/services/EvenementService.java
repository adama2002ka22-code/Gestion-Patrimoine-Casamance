package com.uasz.ms_evenement.services;

import com.uasz.ms_evenement.entities.Evenement;
import com.uasz.ms_evenement.repositories.EvenementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EvenementService {

    private final EvenementRepository repository;

    public EvenementService(EvenementRepository repository) {
        this.repository = repository;
    }

    public Evenement save(Evenement e) {
        if (e.getPlacesMax() == null) e.setPlacesMax(0);
        if (e.getPlacesRestantes() == null) e.setPlacesRestantes(e.getPlacesMax());
        return repository.save(e);
    }

    public List<Evenement> getAll() { return repository.findAll(); }

    public Optional<Evenement> getById(Long id) { return repository.findById(id); }

    public Evenement update(Long id, Evenement n) {
        return repository.findById(id).map(e -> {
            e.setTitre(n.getTitre());
            e.setType(n.getType());
            e.setDescription(n.getDescription());
            e.setDateEvenement(n.getDateEvenement());
            e.setHeureDebut(n.getHeureDebut());
            e.setHeureFin(n.getHeureFin());
            e.setLieu(n.getLieu());
            e.setPlacesMax(n.getPlacesMax());
            e.setPlacesRestantes(n.getPlacesRestantes() != null
                    ? n.getPlacesRestantes() : n.getPlacesMax());
            e.setPatrimoineId(n.getPatrimoineId());
            return repository.save(e);
        }).orElseThrow(() -> new NoSuchElementException("Événement non trouvé : " + id));
    }

    public void delete(Long id) { repository.deleteById(id); }

    // Réserver des places : diminue placesRestantes
    @Transactional
    public Evenement reserverPlaces(Long id, int places) {
        Evenement e = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Événement non trouvé : " + id));
        if (places <= 0) {
            throw new IllegalArgumentException("Le nombre de places doit être positif");
        }
        if (e.getPlacesRestantes() < places) {
            throw new IllegalStateException("Places insuffisantes : " + e.getPlacesRestantes()
                    + " restantes, " + places + " demandées");
        }
        e.setPlacesRestantes(e.getPlacesRestantes() - places);
        return repository.save(e);
    }

    // Libérer des places (utile pour l'annulation d'une réservation)
    @Transactional
    public Evenement libererPlaces(Long id, int places) {
        Evenement e = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Événement non trouvé : " + id));
        e.setPlacesRestantes(Math.min(e.getPlacesMax(), e.getPlacesRestantes() + places));
        return repository.save(e);
    }
}