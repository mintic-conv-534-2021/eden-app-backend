package gov.co.eden.service;

import gov.co.eden.dto.catalogoorganizacion.CatalogoOrganizacionDTO;
import gov.co.eden.entity.CatalogoOrganizacion;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CatalogoOrganizacionService {

    CatalogoOrganizacion getCatalogoOrganizacionById(long catalogoOrganizacionId);

    List<CatalogoOrganizacion> getAllCatalogoOrganizacion();

    void createCatalogoOrganizacion(CatalogoOrganizacion catalogoOrganizacion, MultipartFile imagen) throws IOException;

    void updateCatalogoOrganizacion(CatalogoOrganizacionDTO catalogoOrganizacion, MultipartFile imagen) throws IOException;

}
