package gov.co.eden.service.impl;

import gov.co.eden.dto.catalogoproducto.CatalogoProductoDTO;
import gov.co.eden.entity.CatalogoProducto;
import gov.co.eden.exception.NotFoundException;
import gov.co.eden.repository.CatalogoProductoRepository;
import gov.co.eden.service.CatalogoProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CatalogoProductoServiceImpl implements CatalogoProductoService {

    @Autowired
    private CatalogoProductoRepository catalogoProductoRepository;

    @Override
    public CatalogoProducto getCatalogoById(long catalogoId) {
        Optional<CatalogoProducto> catalogo = catalogoProductoRepository.findById(catalogoId);
        if (!catalogo.isPresent())
            throw new NotFoundException("No catalogo de productos found on database for id: " + catalogoId);
        return catalogo.get();
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
    public void createCatalogo(CatalogoProducto catalogoProducto) {
        catalogoProductoRepository.save(catalogoProducto);
    }

    @Override
    public void updateCatalogo(CatalogoProducto catalogoProducto) {
        catalogoProductoRepository.save(catalogoProducto);
    }
}
