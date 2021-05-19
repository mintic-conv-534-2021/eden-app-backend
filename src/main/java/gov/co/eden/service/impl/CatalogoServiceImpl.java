package gov.co.eden.service.impl;

import gov.co.eden.dto.catalogo.CatalogoDTO;
import gov.co.eden.dto.modulo.ModuloDTO;
import gov.co.eden.entity.Catalogo;
import gov.co.eden.entity.Modulo;
import gov.co.eden.service.CatalogoService;
import gov.co.eden.service.ModuloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CatalogoServiceImpl implements CatalogoService {


    @Override
    public Catalogo getCatalogoById(long catalogoId) {
        return null;
    }

    @Override
    public List<Catalogo> getAllCatalogo() {
        return null;
    }

    @Override
    public void createCatalogo(CatalogoDTO request) {

    }

    @Override
    public void updateCatalogo(CatalogoDTO request) {

    }
}
