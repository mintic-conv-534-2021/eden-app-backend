package gov.co.eden.service;

import gov.co.eden.dto.catalogoproducto.CatalogoProductoDTO;
import gov.co.eden.entity.CatalogoProducto;

import java.util.List;

public interface CatalogoProductoService {

    CatalogoProducto getCatalogoById(long catalogoId);

    List<CatalogoProducto> getCatalogoByCatalogoOrganizacionId(long catalogoOrganizacionId);

    List<CatalogoProducto> getAllCatalogo(Boolean active);

    void createCatalogo(List<CatalogoProducto> catalogoProducto);

    void updateCatalogo(CatalogoProductoDTO catalogoProducto);

    void changeCatalogoState(long catalogoId, Boolean estado);


}
