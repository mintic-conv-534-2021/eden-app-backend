package gov.co.eden.service.impl;

import gov.co.eden.dto.organizacion.OrganizacionDTO;
import gov.co.eden.entity.CatalogoOrganizacion;
import gov.co.eden.entity.CatalogoProducto;
import gov.co.eden.entity.Organizacion;
import gov.co.eden.entity.Producto;
import gov.co.eden.entity.RedSocial;
import gov.co.eden.exception.NotFoundException;
import gov.co.eden.repository.CatalogoOrganizacionRepository;
import gov.co.eden.repository.OrganizacionRepository;
import gov.co.eden.service.OrganizacionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrganizacionServiceImpl implements OrganizacionService {

    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Autowired
    private CatalogoOrganizacionRepository catalogoOrganizacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Organizacion getOrganizacionById(long organizacionId) {
        Optional<Organizacion> modulo = organizacionRepository.findById(organizacionId);
        if (!modulo.isPresent())
            throw new NotFoundException("No organizacion found on database for id: " + organizacionId);
        return modulo.get();
    }

    @Override
    public List<Organizacion> getOrganizacionByCatalogoOrganizacionId(Long catalogoOrganziacionId) {
        List<Organizacion> entities = organizacionRepository.findAllByCatalogoOrganizacion_IdCatalogoOrganizacion(catalogoOrganziacionId);
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
    public void createOrganizacion(OrganizacionDTO request) {
        Organizacion organizacion = modelMapper.map(request, Organizacion.class);
        CatalogoOrganizacion catalogoOrganizacion = catalogoOrganizacionRepository.findById(request.getCatalogoOrganizacionId()).
                orElseThrow(() -> new NotFoundException("Catalogo de organizacion con id "
                        + request.getCatalogoOrganizacionId() + " no existe en la BD"));
        organizacion.setRedSocial(modelMapper.map(request.getRedSocialDTO(), RedSocial.class));
        organizacion.setCatalogoOrganizacion(catalogoOrganizacion);
        organizacionRepository.save(organizacion);
    }

    @Override
    public void updateOrganizacion(OrganizacionDTO request) {
        Organizacion organizacion = organizacionRepository.findById(request.getOrganizacionId()).
                orElseThrow(() -> new NotFoundException("Organizacion con id "
                        + request.getOrganizacionId() + " no existe en la BD"));;
        merge(request, organizacion);
        organizacionRepository.save(organizacion);
    }

    @Override
    public Map<Long, List<Organizacion>> findOrganizationByCatalogoOrganization(){
        return organizacionRepository.findAll().stream().
                collect(Collectors.groupingBy(x -> x.getCatalogoOrganizacion().getIdCatalogoOrganizacion()));
    }

    public static <T> void merge(T source, T target) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(source, target);
    }

}
