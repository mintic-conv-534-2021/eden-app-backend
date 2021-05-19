package gov.co.eden.service.impl;

import gov.co.eden.dto.organizacion.OrganizacionDTO;
import gov.co.eden.entity.Organizacion;
import gov.co.eden.service.OrganizacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrganizacionServiceImpl implements OrganizacionService {


    @Override
    public Organizacion getOrganizacionById(long productoId) {
        return null;
    }

    @Override
    public List<Organizacion> getAllOrganizacion() {
        return null;
    }

    @Override
    public void createOrganizacion(OrganizacionDTO request) {

    }

    @Override
    public void updateOrganizacion(OrganizacionDTO request) {

    }
}
