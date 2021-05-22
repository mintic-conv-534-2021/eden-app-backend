package gov.co.eden.service;

import gov.co.eden.entity.CatalogoOrganizacion;

import java.util.List;

public interface CatalogoOrganizacionService {

    CatalogoOrganizacion getCatalogoOrganizacionById(long moduloId);

    List<CatalogoOrganizacion> getAllCatalogoOrganizacion();

    void createCatalogoOrganizacion(CatalogoOrganizacion catalogoOrganizacion);

    void updateCatalogoOrganizacion(CatalogoOrganizacion catalogoOrganizacion);
}
