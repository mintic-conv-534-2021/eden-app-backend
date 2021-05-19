package gov.co.eden.service.impl;

import gov.co.eden.dto.catalogo.CatalogoDTO;
import gov.co.eden.entity.Catalogo;
import gov.co.eden.exception.NotFoundException;
import gov.co.eden.repository.CatalogoRepository;
import gov.co.eden.service.CatalogoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CatalogoServiceImpl implements CatalogoService {

    @Autowired
    private CatalogoRepository catalogoRepository;

    @Override
    public Catalogo getCatalogoById(long catalogoId) {
        Optional<Catalogo> catalogo = catalogoRepository.findById(catalogoId);
        if (!catalogo.isPresent())
            throw new NotFoundException("No catalogo found on database for id: " + catalogoId);
        return catalogo.get();
    }

    @Override
    public List<Catalogo> getAllCatalogo() {
        List<Catalogo> entities = catalogoRepository.findAll();
        log.info("Found {} of modulo", entities.size());
        if (entities.isEmpty())
            throw new NotFoundException("No modules found on database");
        return entities;
    }

    @Override
    public void createCatalogo(CatalogoDTO request) {

    }

    @Override
    public void updateCatalogo(CatalogoDTO request) {

    }
}
