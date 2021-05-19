package gov.co.eden.service;

import gov.co.eden.dto.producto.ProductoDTO;
import gov.co.eden.entity.Producto;

import java.util.List;

public interface ProductoService {

    Producto getProductoById(long productoId);

    List<Producto> getAllProductos();

    void createProducto(ProductoDTO request);

    void updateProducto(ProductoDTO request);
}
