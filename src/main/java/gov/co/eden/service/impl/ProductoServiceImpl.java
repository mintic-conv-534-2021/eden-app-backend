package gov.co.eden.service.impl;

import gov.co.eden.dto.producto.ProductoDTO;
import gov.co.eden.entity.Producto;
import gov.co.eden.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductoServiceImpl implements ProductoService {


    @Override
    public Producto getProductoById(long productoId) {
        return null;
    }

    @Override
    public List<Producto> getAllProductos() {
        return null;
    }

    @Override
    public void createProducto(ProductoDTO request) {

    }

    @Override
    public void updateProducto(ProductoDTO request) {

    }
}
