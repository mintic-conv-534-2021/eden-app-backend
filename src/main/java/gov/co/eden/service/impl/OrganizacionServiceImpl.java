package gov.co.eden.service.impl;

import gov.co.eden.dto.organizacion.OrganizacionDTO;
import gov.co.eden.entity.Organizacion;
import gov.co.eden.exception.NotFoundException;
import gov.co.eden.repository.OrganizacionRepository;
import gov.co.eden.service.OrganizacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrganizacionServiceImpl implements OrganizacionService {

    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Override
    public Organizacion getOrganizacionById(long organizacionId) {
        Optional<Organizacion> modulo = organizacionRepository.findById(organizacionId);
        if (!modulo.isPresent())
            throw new NotFoundException("No organizacion found on database for id: " + organizacionId);
        return modulo.get();
    }

    @Override
    public List<Organizacion> getAllOrganizacion() {
        List<Organizacion> entities = organizacionRepository.findAll();
        log.info("Found {} of modulo", entities.size());
        if (entities.isEmpty())
            throw new NotFoundException("No organizacion found on database");
        return entities;
    }

    @Override
    public void createOrganizacion(OrganizacionDTO request) {

    }

    @Override
    public void updateOrganizacion(OrganizacionDTO request) {

    }
}
