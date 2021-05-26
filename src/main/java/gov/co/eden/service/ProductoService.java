package gov.co.eden.service;

import gov.co.eden.dto.producto.ProductoDTO;
import gov.co.eden.entity.Producto;

import java.util.List;

public interface ProductoService {

    Producto getProductoById(long productoId);

    List<Producto> getProductoByCatalogoProductoId(Long catalogoProductoId);

    List<Producto> getAllProductos();

    void createProducto(ProductoDTO request);

    void updateProducto(ProductoDTO request);

    void changeProductoState(long productoId, Boolean estado);

    void changeProductosByCatalogoState(long catalogoProductoId);

    void changeProductosByOrganizacionState(long organizacionId);
}
