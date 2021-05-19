package gov.co.eden.service.impl;

import gov.co.eden.dto.producto.ProductoDTO;
import gov.co.eden.entity.Producto;
import gov.co.eden.exception.NotFoundException;
import gov.co.eden.repository.ProductoRepository;
import gov.co.eden.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto getProductoById(long productoId) {
        Optional<Producto> catalogo = productoRepository.findById(productoId);
        if (!catalogo.isPresent())
            throw new NotFoundException("No product found on database for id: " + productoId);
        return catalogo.get();
    }

    @Override
    public List<Producto> getAllProductos() {
        List<Producto> entities = productoRepository.findAll();
        log.info("Found {} of modulo", entities.size());
        if (entities.isEmpty())
            throw new NotFoundException("No modules found on database");
        return entities;
    }

    @Override
    public void createProducto(ProductoDTO request) {

    }

    @Override
    public void updateProducto(ProductoDTO request) {

    }
}
