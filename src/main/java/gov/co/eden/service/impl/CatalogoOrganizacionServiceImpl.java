package gov.co.eden.service.impl;

import gov.co.eden.entity.CatalogoOrganizacion;
import gov.co.eden.exception.NotFoundException;
import gov.co.eden.repository.CatalogoOrganizacionRepository;
import gov.co.eden.service.CatalogoOrganizacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CatalogoOrganizacionServiceImpl implements CatalogoOrganizacionService {

    @Autowired
    private CatalogoOrganizacionRepository catalogoOrganizacionRepository;

    @Override
    public CatalogoOrganizacion getCatalogoOrganizacionById(long moduloId) {
        Optional<CatalogoOrganizacion> modulo = catalogoOrganizacionRepository.findById(moduloId);
        if (!modulo.isPresent())
            throw new NotFoundException("No catalogo de organizacion found on database for id: " + moduloId);
        return modulo.get();
    }

    @Override
    public List<CatalogoOrganizacion> getAllCatalogoOrganizacion() {
        List<CatalogoOrganizacion> entities = catalogoOrganizacionRepository.findAll();
        log.info("Found {} of modulo", entities.size());
        if (entities.isEmpty())
            throw new NotFoundException("No catalogo de organizacion found on database");
        return entities;
    }

    @Override
    public void createCatalogoOrganizacion(CatalogoOrganizacion catalogoOrganizacion) {
        catalogoOrganizacion.setActivo(true);
        catalogoOrganizacionRepository.save(catalogoOrganizacion);
    }

    @Override
    public void updateCatalogoOrganizacion(CatalogoOrganizacion catalogoOrganizacion) {
        catalogoOrganizacionRepository.save(catalogoOrganizacion);
    }
}
