package com.uasz.ms_patrimoine.services;

import com.uasz.ms_patrimoine.entities.Patrimoine;
import com.uasz.ms_patrimoine.repositories.PatrimoineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatrimoineService implements IPatrimoineService {

    @Autowired
    private PatrimoineRepository patrimoineRepository;

    @Override
    public Patrimoine savePatrimoine(Patrimoine patrimoine) {
        return patrimoineRepository.save(patrimoine);
    }

    @Override
    public List<Patrimoine> getAllPatrimoines() {
        return patrimoineRepository.findAll();
    }

    @Override
    public Optional<Patrimoine> getByIdPatrimoine(Long id) {
        return patrimoineRepository.findById(id);
    }

    @Override
    public Patrimoine updatePatrimoine(Long id, Patrimoine newPatrimoine) {
        return patrimoineRepository.findById(id).map(patrimoine -> {
            patrimoine.setNomPatrimoine(newPatrimoine.getNomPatrimoine());
            patrimoine.setCodePatrimoine(newPatrimoine.getCodePatrimoine());
            patrimoine.setDescriptionPatrimoine(newPatrimoine.getDescriptionPatrimoine());
            patrimoine.setHistoriquePatrimoine(newPatrimoine.getHistoriquePatrimoine());
            patrimoine.setAdressePatrimoine(newPatrimoine.getAdressePatrimoine());
            patrimoine.setCapacitePatrimoine(newPatrimoine.getCapacitePatrimoine());
            patrimoine.setLatitudePatrimoine(newPatrimoine.getLatitudePatrimoine());
            patrimoine.setLongitudePatrimoine(newPatrimoine.getLongitudePatrimoine());
            return patrimoineRepository.save(patrimoine);
        }).orElseThrow(() -> new RuntimeException("Patrimoine non trouvé avec l'id : " + id));
    }

    @Override
    public void deletePatrimoine(Long id) {
        patrimoineRepository.deleteById(id);
    }
}