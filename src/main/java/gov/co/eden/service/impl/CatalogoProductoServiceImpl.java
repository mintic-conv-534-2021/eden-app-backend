package gov.co.eden.service.impl;

import gov.co.eden.dto.catalogoproducto.CatalogoProductoDTO;
import gov.co.eden.entity.CatalogoOrganizacion;
import gov.co.eden.entity.CatalogoProducto;
import gov.co.eden.exception.NotFoundException;
import gov.co.eden.repository.CatalogoProductoRepository;
import gov.co.eden.service.CatalogoProductoService;
import gov.co.eden.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CatalogoProductoServiceImpl implements CatalogoProductoService {

    @Autowired
    private CatalogoProductoRepository catalogoProductoRepository;
    @Autowired
    private ProductoService productoService;

    @Override
    public CatalogoProducto getCatalogoById(long catalogoId) {
        Optional<CatalogoProducto> catalogo = catalogoProductoRepository.findById(catalogoId);
        if (!catalogo.isPresent())
            throw new NotFoundException("No catalogo de productos found on database for id: " + catalogoId);
        return catalogo.get();
    }

    @Override
    public List<CatalogoProducto> getCatalogoByCatalogoOrganizacionId(long catalogoOrganizacionId) {
        List<CatalogoProducto> catalogo = catalogoProductoRepository.findAllByCatalogoOrganizacion_CatalogoOrganizacionId(catalogoOrganizacionId);
        if (catalogo.isEmpty())
            throw new NotFoundException("No catalogo de productos found on database for catalogo organizacion id: " + catalogoOrganizacionId);
        return catalogo;
    }

    @Override
    public List<CatalogoProducto> getAllCatalogo() {
        List<CatalogoProducto> entities = catalogoProductoRepository.findAll();
        log.info("Found {} of modulo", entities.size());
        if (entities.isEmpty())
            throw new NotFoundException("No catalogo de productos found on database");
        return entities;
    }

    @Override
    public void createCatalogo(List<CatalogoProducto> catalogoProducto) {
        catalogoProducto.stream().forEach(catalogo -> catalogo.setActivo(true));
        catalogoProductoRepository.saveAll(catalogoProducto);
    }

    @Override
    public void changeCatalogoState(long catalogoId, Boolean estado) {
        CatalogoProducto catalogoProducto = catalogoProductoRepository.findById(catalogoId).
                orElseThrow(() -> new NotFoundException("Catalogo de productos con id "
                        + catalogoId + " no existe en la BD"));
        catalogoProducto.setActivo(estado);
        catalogoProductoRepository.save(catalogoProducto);
        productoService.changeProductosByCatalogoState(catalogoProducto.getIdCatalogoProducto());
    }

    @Override
    public void updateCatalogo(CatalogoProductoDTO catalogoProducto) {
        CatalogoProducto catalogoProductoEntity = catalogoProductoRepository.findById(catalogoProducto.getCatalogoProductoId()).
                orElseThrow(() -> new NotFoundException("No catalogo de producto found on database for id: " +
                        catalogoProducto.getCatalogoProductoId()));
        merge(catalogoProducto, catalogoProductoEntity);
        catalogoProductoRepository.save(catalogoProductoEntity);
    }

    public static <T> void merge(T source, T target) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(source, target);
    }
}
