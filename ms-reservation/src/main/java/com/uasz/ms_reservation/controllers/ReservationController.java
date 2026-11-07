package com.uasz.ms_reservation.controllers;

import com.uasz.ms_reservation.entities.Reservation;
import com.uasz.ms_reservation.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) { this.service = service; }

    @PostMapping
    public Reservation create(@RequestBody Reservation r) { return service.create(r); }

    @GetMapping
    public List<Reservation> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/confirmer")
    public ResponseEntity<Reservation> confirmer(@PathVariable Long id) {
        try { return ResponseEntity.ok(service.confirmer(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<Reservation> annuler(@PathVariable Long id) {
        try { return ResponseEntity.ok(service.annuler(id)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}