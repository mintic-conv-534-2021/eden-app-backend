package gov.co.eden.service;

import gov.co.eden.dto.organizacion.OrganizacionDTO;
import gov.co.eden.entity.Organizacion;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface OrganizacionService {

    Organizacion getOrganizacionById(long organizacionId);

    List<Organizacion> getOrganizacionByCatalogoOrganizacionId(Long catalogoOrganziacionId);

    List<Organizacion> getAllOrganizacion();

    void createOrganizacion(OrganizacionDTO request, MultipartFile logo, MultipartFile banner, MultipartFile rm,
                            MultipartFile rut, MultipartFile rnt) throws IOException;

    void updateOrganizacion(OrganizacionDTO request, MultipartFile logo, MultipartFile banner, MultipartFile rm,
                            MultipartFile rut, MultipartFile rnt) throws IOException;

    Map<Long, List<Organizacion>> findOrganizationByCatalogoOrganization();

    void changeOrganizationState(long organizacionId, Boolean estado);
}
