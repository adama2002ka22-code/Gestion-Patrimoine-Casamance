package com.uasz.ms_circuit.controllers;

import com.uasz.ms_circuit.clients.PatrimoineClient;
import com.uasz.ms_circuit.dto.EtapeDetailDto;
import com.uasz.ms_circuit.dto.PatrimoineDto;
import com.uasz.ms_circuit.entities.Circuit;
import com.uasz.ms_circuit.entities.Etape;
import com.uasz.ms_circuit.services.CircuitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@RestController
@RequestMapping("/api/circuits")
public class CircuitController {

    private final CircuitService service;
    private final PatrimoineClient patrimoineClient;
    private final RestTemplate restTemplate;
    private final WebClient webClient;

    public CircuitController(CircuitService service,
                             PatrimoineClient patrimoineClient,
                             RestTemplate restTemplate,
                             WebClient webClient) {
        this.service = service;
        this.patrimoineClient = patrimoineClient;
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    @PostMapping
    public Circuit create(@RequestBody Circuit c) { return service.saveCircuit(c); }

    @GetMapping
    public List<Circuit> getAll() { return service.getAllCircuits(); }

    @GetMapping("/{id}")
    public ResponseEntity<Circuit> getById(@PathVariable Long id) {
        return service.getCircuitById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Circuit> update(@PathVariable Long id, @RequestBody Circuit c) {
        try { return ResponseEntity.ok(service.updateCircuit(id, c)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCircuit(id);
        return ResponseEntity.noContent().build();
    }

    // Étapes d'un circuit
    @PostMapping("/{id}/etapes")
    public ResponseEntity<Etape> addEtape(@PathVariable Long id, @RequestBody Etape e) {
        try { return ResponseEntity.ok(service.addEtape(id, e)); }
        catch (RuntimeException ex) { return ResponseEntity.notFound().build(); }
    }

    @GetMapping("/{id}/etapes")
    public List<Etape> getEtapes(@PathVariable Long id) { return service.getEtapes(id); }

    @DeleteMapping("/etapes/{etapeId}")
    public ResponseEntity<Void> deleteEtape(@PathVariable Long etapeId) {
        service.deleteEtape(etapeId);
        return ResponseEntity.noContent().build();
    }

    // 1) OpenFeign
    @GetMapping("/{id}/etapes-details")
    public List<EtapeDetailDto> getEtapesDetails(@PathVariable Long id) {
        return service.getEtapes(id).stream()
                .map(e -> new EtapeDetailDto(
                        e.getId(),
                        e.getPosition(),
                        e.getDureeEstime(),
                        patrimoineClient.getPatrimoineById(e.getPatrimoineId())))
                .toList();
    }

    // 2) RestTemplate
    @GetMapping("/patrimoine-resttemplate/{patrimoineId}")
    public PatrimoineDto getPatrimoineViaRestTemplate(@PathVariable Long patrimoineId) {
        return restTemplate.getForObject(
                "http://localhost:3001/api/patrimoines/" + patrimoineId,
                PatrimoineDto.class);
    }

    // 3) WebClient
    @GetMapping("/patrimoine-webclient/{patrimoineId}")
    public PatrimoineDto getPatrimoineViaWebClient(@PathVariable Long patrimoineId) {
        return webClient.get()
                .uri("/api/patrimoines/{id}", patrimoineId)
                .retrieve()
                .bodyToMono(PatrimoineDto.class)
                .block();
    }
}