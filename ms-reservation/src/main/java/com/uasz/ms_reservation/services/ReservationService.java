package com.uasz.ms_reservation.services;

import com.uasz.ms_reservation.clients.CircuitClient;
import com.uasz.ms_reservation.clients.EvenementClient;
import com.uasz.ms_reservation.dto.EvenementDto;
import com.uasz.ms_reservation.entities.Reservation;
import com.uasz.ms_reservation.repositories.ReservationRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final CircuitClient circuitClient;
    private final EvenementClient evenementClient;

    public ReservationService(ReservationRepository repository,
                              CircuitClient circuitClient,
                              EvenementClient evenementClient) {
        this.repository = repository;
        this.circuitClient = circuitClient;
        this.evenementClient = evenementClient;
    }

    // Création : vérifie la disponibilité avant de valider
    public Reservation create(Reservation r) {
        r.setDateReservation(LocalDateTime.now());
        r.setStatut(verifierDisponibilite(r) ? "CONFIRMEE" : "REFUSEE");
        return repository.save(r);
    }

    private boolean verifierDisponibilite(Reservation r) {
        try {
            if ("CIRCUIT".equalsIgnoreCase(r.getTypeReservation())) {
                // Le circuit doit exister
                return circuitClient.getCircuitById(r.getRefId()) != null;
            }
            if ("EVENEMENT".equalsIgnoreCase(r.getTypeReservation())) {
                EvenementDto e = evenementClient.getEvenementById(r.getRefId());
                if (e == null || e.getPlacesRestantes() < r.getNombrePersonnes()) {
                    return false;
                }
                // Diminue les places dans ms-evenement
                evenementClient.reserverPlaces(r.getRefId(), r.getNombrePersonnes());
                return true;
            }
            return false; // type inconnu
        } catch (FeignException ex) {
            return false; // 404, 409, service indisponible...
        }
    }

    public List<Reservation> getAll() { return repository.findAll(); }

    public Optional<Reservation> getById(Long id) { return repository.findById(id); }

    public Reservation confirmer(Long id) { return changerStatut(id, "CONFIRMEE"); }

    // Annulation : rend les places à l'événement
    public Reservation annuler(Long id) {
        Reservation r = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée : " + id));
        if ("CONFIRMEE".equals(r.getStatut()) && "EVENEMENT".equalsIgnoreCase(r.getTypeReservation())) {
            try {
                evenementClient.libererPlaces(r.getRefId(), r.getNombrePersonnes());
            } catch (FeignException ignored) { }
        }
        r.setStatut("ANNULEE");
        return repository.save(r);
    }

    private Reservation changerStatut(Long id, String statut) {
        Reservation r = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée : " + id));
        r.setStatut(statut);
        return repository.save(r);
    }

    public void delete(Long id) { repository.deleteById(id); }
}