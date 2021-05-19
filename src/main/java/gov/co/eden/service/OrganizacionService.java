package gov.co.eden.service;

import gov.co.eden.dto.organizacion.OrganizacionDTO;
import gov.co.eden.dto.producto.ProductoDTO;
import gov.co.eden.entity.Organizacion;
import gov.co.eden.entity.Producto;

import java.util.List;

public interface OrganizacionService {

    Organizacion getOrganizacionById(long productoId);

    List<Organizacion> getAllOrganizacion();

    void createOrganizacion(OrganizacionDTO request);

    void updateOrganizacion(OrganizacionDTO request);
}
