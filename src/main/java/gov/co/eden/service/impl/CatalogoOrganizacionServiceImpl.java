package gov.co.eden.service.impl;

import gov.co.eden.dto.catalogoorganizacion.CatalogoOrganizacionDTO;
import gov.co.eden.entity.CatalogoOrganizacion;
import gov.co.eden.entity.CatalogoProducto;
import gov.co.eden.exception.NotFoundException;
import gov.co.eden.repository.CatalogoOrganizacionRepository;
import gov.co.eden.service.AWSS3Service;
import gov.co.eden.service.CatalogoOrganizacionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CatalogoOrganizacionServiceImpl implements CatalogoOrganizacionService {

    @Autowired
    private AWSS3Service awss3Service;

    @Autowired
    private CatalogoOrganizacionRepository catalogoOrganizacionRepository;

    private final static String CATALOG_ORGANIZATION_PATH = "catalogo-organizacion";

    @Override
    public CatalogoOrganizacion getCatalogoOrganizacionById(long catalogoOrganizacionId) {
        Optional<CatalogoOrganizacion> modulo = catalogoOrganizacionRepository.findById(catalogoOrganizacionId);
        if (!modulo.isPresent())
            throw new NotFoundException("No catalogo de organizacion found on database for id: " + catalogoOrganizacionId);
        return modulo.get();
    }

    @Override
    public List<CatalogoOrganizacion> getAllCatalogoOrganizacion(Boolean filtrarActivos) {
        List<CatalogoOrganizacion> entities;
        if(filtrarActivos)
            entities = catalogoOrganizacionRepository.findAllByActivo(true);
        else
            entities = catalogoOrganizacionRepository.findAll();
        log.info("Found {} of modulo", entities.size());
        if (entities.isEmpty())
            throw new NotFoundException("No catalogo de organizacion found on database");
        return entities;
    }

    @Override
    public void createCatalogoOrganizacion(CatalogoOrganizacion catalogoOrganizacion, MultipartFile imagen) throws IOException {
        catalogoOrganizacion.setActivo(true);
        catalogoOrganizacion.setUrlImagen(awss3Service.uploadFile(imagen, CATALOG_ORGANIZATION_PATH));
        catalogoOrganizacionRepository.save(catalogoOrganizacion);
    }

    @Override
    public void updateCatalogoOrganizacion(CatalogoOrganizacionDTO catalogoOrganizacion, MultipartFile imagen) throws IOException {
        CatalogoOrganizacion catalogoOrganizacionEntity = catalogoOrganizacionRepository.findById(catalogoOrganizacion.getCatalogoOrganizacionId()).
                orElseThrow(() -> new NotFoundException("No catalogo de organizacion found on database for id: " +
                        catalogoOrganizacion.getCatalogoOrganizacionId()));
        merge(catalogoOrganizacion, catalogoOrganizacionEntity);
        if (imagen != null) {
            awss3Service.deleteObject(catalogoOrganizacionEntity.getUrlImagen());
            catalogoOrganizacionEntity.setUrlImagen(awss3Service.uploadFile(imagen, CATALOG_ORGANIZATION_PATH));
        }
        catalogoOrganizacionRepository.save(catalogoOrganizacionEntity);
    }

    @Override
    public void changeCatalogoState(Long catalogoOrganizacionId, Boolean estado) {
        CatalogoOrganizacion catalogoOrganizacion = catalogoOrganizacionRepository.findById(catalogoOrganizacionId).
                orElseThrow(() -> new NotFoundException("Catalogo organizacion con id "
                        + catalogoOrganizacionId + " no existe en la BD"));
        catalogoOrganizacion.setActivo(estado);
        catalogoOrganizacionRepository.save(catalogoOrganizacion);
    }

    public static <T> void merge(T source, T target) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(source, target);
    }

}
