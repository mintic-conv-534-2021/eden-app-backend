package gov.co.eden.service;

import gov.co.eden.dto.modulo.ModuloDTO;
import gov.co.eden.entity.Modulo;

import java.util.List;

public interface ModuloService {

    Modulo getModuloById(long moduloId);

    List<Modulo> getAllModulo();

    void createModulo(ModuloDTO request);

    void updateModulo(ModuloDTO request);
}
