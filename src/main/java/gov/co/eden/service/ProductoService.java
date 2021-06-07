package gov.co.eden.service;

import gov.co.eden.dto.producto.ProductoDTO;
import gov.co.eden.entity.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductoService {

    Producto getProductoById(long productoId);

    List<Producto> getProductoByCatalogoProductoId(Long catalogoProductoId);

    List<Producto> getAllProductos(Boolean activo);

    void createProducto(ProductoDTO request, MultipartFile imagen) throws IOException;

    void updateProducto(ProductoDTO request, MultipartFile imagen) throws IOException;

    void changeProductoState(long productoId, Boolean estado);

    void changeProductosByCatalogoState(long catalogoProductoId);

    void changeProductosByOrganizacionState(long organizacionId);
}
