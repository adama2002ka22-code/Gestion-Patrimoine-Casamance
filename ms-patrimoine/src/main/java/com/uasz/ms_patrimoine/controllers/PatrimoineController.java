package com.uasz.ms_patrimoine.controllers;

import com.uasz.ms_patrimoine.entities.Patrimoine;
import com.uasz.ms_patrimoine.services.PatrimoineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patrimoines")
public class PatrimoineController {

    private final PatrimoineService patrimoineService;

    // Injection via le constructeur (recommandé)
    public PatrimoineController(PatrimoineService patrimoineService) {
        this.patrimoineService = patrimoineService;
    }

    // 1. Ajouter (POST)
    @PostMapping
    public Patrimoine createPatrimoine(@RequestBody Patrimoine patrimoine) {
        return patrimoineService.savePatrimoine(patrimoine);
    }

    // 2. Récupérer tous (GET)
    @GetMapping
    public List<Patrimoine> getAllPatrimoines() {
        return patrimoineService.getAllPatrimoines();
    }

    // 3. Récupérer par id (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Patrimoine> getPatrimoineById(@PathVariable Long id) {
        return patrimoineService.getByIdPatrimoine(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Modifier (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Patrimoine> updatePatrimoine(@PathVariable Long id,
                                                       @RequestBody Patrimoine patrimoine) {
        try {
            return ResponseEntity.ok(patrimoineService.updatePatrimoine(id, patrimoine));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Supprimer (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatrimoine(@PathVariable Long id) {
        patrimoineService.deletePatrimoine(id);
        return ResponseEntity.noContent().build();
    }
}