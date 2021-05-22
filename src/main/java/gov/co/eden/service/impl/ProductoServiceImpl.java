package gov.co.eden.service.impl;

import gov.co.eden.dto.organizacion.RedSocialDTO;
import gov.co.eden.dto.producto.ProductoDTO;
import gov.co.eden.entity.CatalogoProducto;
import gov.co.eden.entity.Organizacion;
import gov.co.eden.entity.Producto;
import gov.co.eden.entity.RedSocial;
import gov.co.eden.exception.NotFoundException;
import gov.co.eden.repository.CatalogoProductoRepository;
import gov.co.eden.repository.OrganizacionRepository;
import gov.co.eden.repository.ProductoRepository;
import gov.co.eden.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CatalogoProductoRepository catalogoProductoRepository;

    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Autowired
    private ModelMapper modelMapper;

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
        Producto producto = modelMapper.map(request, Producto.class);
        CatalogoProducto catalogoProducto = catalogoProductoRepository.findById(request.getCatalogoProductoId()).
                orElseThrow(() -> new NotFoundException("Catalogo de producto con id "
                        + request.getCatalogoProductoId() + " no existe en la BD"));
        Organizacion organizacion = organizacionRepository.findById(request.getOrganizacionId()).
                orElseThrow(() -> new NotFoundException("Organizacion con id "
                        + request.getOrganizacionId() + " no existe en la BD"));
        producto.setOrganizacion(organizacion);
        producto.setCatalogoProducto(catalogoProducto);
        productoRepository.save(producto);
    }

    @Override
    public void updateProducto(ProductoDTO request) {

        Producto producto = productoRepository.findById(request.getProductoId()).
                orElseThrow(() -> new NotFoundException("Producto con id "
                        + request.getProductoId() + " no existe en la BD"));;
        merge(request, producto);
        productoRepository.save(producto);
    }

    public static <T> void merge(T source, T target) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(source, target);
    }

}
