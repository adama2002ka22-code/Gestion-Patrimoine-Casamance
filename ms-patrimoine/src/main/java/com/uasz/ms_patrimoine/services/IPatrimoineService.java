package com.uasz.ms_patrimoine.services;

import com.uasz.ms_patrimoine.entities.Patrimoine;
import java.util.List;
import java.util.Optional;

public interface IPatrimoineService {
    Patrimoine savePatrimoine(Patrimoine patrimoine);
    List<Patrimoine> getAllPatrimoines();
    Optional<Patrimoine> getByIdPatrimoine(Long id);
    Patrimoine updatePatrimoine(Long id, Patrimoine newPatrimoine);
    void deletePatrimoine(Long id);
}