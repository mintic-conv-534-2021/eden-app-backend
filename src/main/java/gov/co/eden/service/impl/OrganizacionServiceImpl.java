package gov.co.eden.service.impl;

import gov.co.eden.dto.organizacion.OrganizacionDTO;
import gov.co.eden.entity.CatalogoOrganizacion;
import gov.co.eden.entity.Organizacion;
import gov.co.eden.entity.RedSocial;
import gov.co.eden.exception.NotFoundException;
import gov.co.eden.repository.CatalogoOrganizacionRepository;
import gov.co.eden.repository.OrganizacionRepository;
import gov.co.eden.service.AWSS3Service;
import gov.co.eden.service.OrganizacionService;
import gov.co.eden.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrganizacionServiceImpl implements OrganizacionService {

    @Autowired
    private AWSS3Service aws3Service;

    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Autowired
    private CatalogoOrganizacionRepository catalogoOrganizacionRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ModelMapper modelMapper;

    private final static String ORGANIZATION_PATH_LOGO = "organizacion/logo";
    private final static String ORGANIZATION_PATH_BANNER = "organizacion/banner";
    private final static String ORGANIZATION_PATH_RM = "organizacion/rm";
    private final static String ORGANIZATION_PATH_RNT = "organizacion/rnt";
    private final static String ORGANIZATION_PATH_RUT = "organizacion/rut";

    @Override
    public Organizacion getOrganizacionById(long organizacionId) {
        Optional<Organizacion> modulo = organizacionRepository.findById(organizacionId);
        if (!modulo.isPresent())
            throw new NotFoundException("No organizacion found on database for id: " + organizacionId);
        return modulo.get();
    }

    @Override
    public List<Organizacion> getOrganizacionByCatalogoOrganizacionId(Long catalogoOrganziacionId) {
        List<Organizacion> entities = organizacionRepository.findAllByCatalogoOrganizacion_CatalogoOrganizacionId(catalogoOrganziacionId);
        if (entities.isEmpty())
            throw new NotFoundException("No organizacion found on database");
        return entities;
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
    public void createOrganizacion(OrganizacionDTO request, MultipartFile logo, MultipartFile banner,
                                   MultipartFile rm, MultipartFile rut, MultipartFile rnt) throws IOException {
        Organizacion organizacion = modelMapper.map(request, Organizacion.class);
        organizacion.setUrlLogo(aws3Service.uploadFile(logo,ORGANIZATION_PATH_LOGO));
        organizacion.setUrlBanner(aws3Service.uploadFile(banner,ORGANIZATION_PATH_BANNER));
        organizacion.setUrlRM(aws3Service.uploadFile(rm,ORGANIZATION_PATH_RM));
        organizacion.setUrlRut(aws3Service.uploadFile(rut,ORGANIZATION_PATH_RUT));
        if(rnt != null)
            organizacion.setUrlRNT(aws3Service.uploadFile(rnt,ORGANIZATION_PATH_RNT));
        CatalogoOrganizacion catalogoOrganizacion = catalogoOrganizacionRepository.findById(request.getCatalogoOrganizacionId()).
                orElseThrow(() -> new NotFoundException("Catalogo de organizacion con id "
                        + request.getCatalogoOrganizacionId() + " no existe en la BD"));
        organizacion.setRedSocial(modelMapper.map(request.getRedSocial(), RedSocial.class));
        organizacion.setCatalogoOrganizacion(catalogoOrganizacion);
        organizacion.setActivo(true);
        organizacionRepository.save(organizacion);
    }

    @Override
    public void updateOrganizacion(OrganizacionDTO request, MultipartFile logo, MultipartFile banner, MultipartFile rm, MultipartFile rut, MultipartFile rnt) throws IOException {
        Organizacion organizacion = organizacionRepository.findById(request.getOrganizacionId()).
                orElseThrow(() -> new NotFoundException("Organizacion con id "
                        + request.getOrganizacionId() + " no existe en la BD"));
        merge(request, organizacion);
        if (logo != null) {
            aws3Service.deleteObject(organizacion.getUrlLogo());
            organizacion.setUrlLogo(aws3Service.uploadFile(logo, ORGANIZATION_PATH_LOGO));
        }
        if (banner != null) {
            aws3Service.deleteObject(organizacion.getUrlBanner());
            organizacion.setUrlBanner(aws3Service.uploadFile(banner, ORGANIZATION_PATH_BANNER));
        }
        if (rm != null) {
            aws3Service.deleteObject(organizacion.getUrlRM());
            organizacion.setUrlRM(aws3Service.uploadFile(rm, ORGANIZATION_PATH_RM));
        }
        if (rut != null) {
            aws3Service.deleteObject(organizacion.getUrlRut());
            organizacion.setUrlRut(aws3Service.uploadFile(rut, ORGANIZATION_PATH_RUT));
        }
        if(rnt != null) {
            aws3Service.deleteObject(organizacion.getUrlRNT());
            organizacion.setUrlRNT(aws3Service.uploadFile(rnt, ORGANIZATION_PATH_RNT));
        }
        organizacionRepository.save(organizacion);
    }

    @Override
    public Map<Long, List<Organizacion>> findOrganizationByCatalogoOrganization(){
        return organizacionRepository.findAll().stream().
                collect(Collectors.groupingBy(x -> x.getCatalogoOrganizacion().getCatalogoOrganizacionId()));
    }

    @Override
    public void changeOrganizationState(long organizacionId, Boolean estado) {
        Organizacion organizacion = organizacionRepository.findById(organizacionId).
                orElseThrow(() -> new NotFoundException("Organizacion con id "
                        + organizacionId + " no existe en la BD"));
        organizacion.setActivo(estado);
        organizacionRepository.save(organizacion);
        productoService.changeProductosByOrganizacionState(organizacion.getIdOrganizacion());
    }

    public static <T> void merge(T source, T target) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(source, target);
    }

}
