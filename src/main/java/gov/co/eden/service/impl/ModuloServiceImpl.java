package gov.co.eden.service.impl;

import gov.co.eden.dto.modulo.ModuloDTO;
import gov.co.eden.entity.Modulo;
import gov.co.eden.service.ModuloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ModuloServiceImpl implements ModuloService {

    @Override
    public Modulo getModuloById(long moduloId) {
        return null;
    }

    @Override
    public List<Modulo> getAllModulo() {
        return null;
    }

    @Override
    public void createModulo(ModuloDTO request) {

    }

    @Override
    public void updateModulo(ModuloDTO request) {

    }
}
