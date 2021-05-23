package gov.co.eden.service;

import gov.co.eden.dto.organizacion.OrganizacionDTO;
import gov.co.eden.dto.producto.ProductoDTO;
import gov.co.eden.entity.Organizacion;
import gov.co.eden.entity.Producto;

import java.util.List;
import java.util.Map;

public interface OrganizacionService {

    Organizacion getOrganizacionById(long organizacionId);

    List<Organizacion> getOrganizacionByCatalogoOrganizacionId(Long catalogoOrganziacionId);

    List<Organizacion> getAllOrganizacion();

    void createOrganizacion(OrganizacionDTO request);

    void updateOrganizacion(OrganizacionDTO request);

    Map<Long, List<Organizacion>> findOrganizationByCatalogoOrganization();
}
