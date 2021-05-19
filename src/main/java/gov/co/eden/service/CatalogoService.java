package gov.co.eden.service;

import gov.co.eden.dto.catalogo.CatalogoDTO;
import gov.co.eden.entity.Catalogo;

import java.util.List;

public interface CatalogoService {

    Catalogo getCatalogoById(long catalogoId);

    List<Catalogo> getAllCatalogo();

    void createCatalogo(CatalogoDTO request);

    void updateCatalogo(CatalogoDTO request);
}
