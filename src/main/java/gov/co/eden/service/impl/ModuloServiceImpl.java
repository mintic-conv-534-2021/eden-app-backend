package gov.co.eden.service.impl;

import gov.co.eden.dto.modulo.ModuloDTO;
import gov.co.eden.entity.Modulo;
import gov.co.eden.exception.NotFoundException;
import gov.co.eden.repository.ModuloRepository;
import gov.co.eden.service.ModuloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ModuloServiceImpl implements ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    @Override
    public Modulo getModuloById(long moduloId) {
        Optional<Modulo> modulo = moduloRepository.findById(moduloId);
        if (!modulo.isPresent())
            throw new NotFoundException("No modulo found on database for id: " + moduloId);
        return modulo.get();
    }

    @Override
    public List<Modulo> getAllModulo() {
        List<Modulo> entities = moduloRepository.findAll();
        log.info("Found {} of modulo", entities.size());
        if (entities.isEmpty())
            throw new NotFoundException("No modules found on database");
        return entities;
    }

    @Override
    public void createModulo(ModuloDTO request) {

    }

    @Override
    public void updateModulo(ModuloDTO request) {

    }
}
