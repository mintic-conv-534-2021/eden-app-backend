package gov.co.eden.service;

import gov.co.eden.entity.CatalogoProducto;

import java.util.List;

public interface CatalogoProductoService {

    CatalogoProducto getCatalogoById(long catalogoId);

    List<CatalogoProducto> getAllCatalogo();

    void createCatalogo(List<CatalogoProducto> catalogoProducto);

    void updateCatalogo(CatalogoProducto catalogoProducto);

    void changeCatalogoState(long catalogoId, Boolean estado);
}
