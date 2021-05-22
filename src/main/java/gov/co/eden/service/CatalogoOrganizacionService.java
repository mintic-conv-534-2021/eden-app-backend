package gov.co.eden.service;

import gov.co.eden.entity.CatalogoOrganizacion;

import java.util.List;

public interface CatalogoOrganizacionService {

    CatalogoOrganizacion getModuloById(long moduloId);

    List<CatalogoOrganizacion> getAllModulo();

    void createModulo(CatalogoOrganizacion catalogoOrganizacion);

    void updateModulo(CatalogoOrganizacion catalogoOrganizacion);
}
