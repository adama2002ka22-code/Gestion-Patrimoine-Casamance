package com.uasz.ms_evenement.controllers;

import com.uasz.ms_evenement.entities.Evenement;
import com.uasz.ms_evenement.services.EvenementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/evenements")
public class EvenementController {

    private final EvenementService service;

    public EvenementController(EvenementService service) {
        this.service = service;
    }

    @PostMapping
    public Evenement create(@RequestBody Evenement e) { return service.save(e); }

    @GetMapping
    public List<Evenement> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Evenement> getById(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evenement> update(@PathVariable Long id, @RequestBody Evenement e) {
        try {
            return ResponseEntity.ok(service.update(id, e));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Réserver des places : 200 si OK, 404 si introuvable, 409 si plus assez de places
    @PutMapping("/{id}/reserver")
    public ResponseEntity<Evenement> reserver(@PathVariable Long id, @RequestParam int places) {
        try {
            return ResponseEntity.ok(service.reserverPlaces(id, places));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // Libérer des places (annulation)
    @PutMapping("/{id}/liberer")
    public ResponseEntity<Evenement> liberer(@PathVariable Long id, @RequestParam int places) {
        try {
            return ResponseEntity.ok(service.libererPlaces(id, places));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}