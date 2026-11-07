package com.uasz.ms_reservation.clients;

import com.uasz.ms_reservation.dto.EvenementDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "ms-evenement", url = "${clients.evenement.url:http://localhost:3003}")
//@FeignClient(name = "ms-evenement", url = "http://localhost:3003")
public interface EvenementClient {

    @GetMapping("/api/evenements/{id}")
    EvenementDto getEvenementById(@PathVariable("id") Long id);

    @PutMapping("/api/evenements/{id}/reserver")
    EvenementDto reserverPlaces(@PathVariable("id") Long id, @RequestParam("places") int places);

    @PutMapping("/api/evenements/{id}/liberer")
    EvenementDto libererPlaces(@PathVariable("id") Long id, @RequestParam("places") int places);
}