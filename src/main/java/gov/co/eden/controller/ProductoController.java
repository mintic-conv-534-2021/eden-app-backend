package gov.co.eden.controller;

import gov.co.eden.dto.catalogo.CatalogoListResponse;
import gov.co.eden.dto.catalogo.CatalogoResponse;
import gov.co.eden.dto.producto.ProductoDTO;
import gov.co.eden.dto.producto.ProductoListResponse;
import gov.co.eden.dto.producto.ProductoResponse;
import gov.co.eden.entity.Producto;
import gov.co.eden.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Obtiene el producto de acuerdo al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene el producto de acuerdo al id",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductoResponse> getProducto(@PathVariable("id") long id) {
        Producto producto = productoService.getProductoById(id);
        ProductoDTO productoDTO = modelMapper.map(producto, ProductoDTO.class);
        ProductoResponse response = ProductoResponse
                .builder()
                .productoDTO(productoDTO)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Obtiene lista de productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista de productos",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductoListResponse> getProductoList() {
        List<Producto> productoList = productoService.getAllProductos();
        List<ProductoDTO> productoDTOList = convertToProductos(productoList);
        ProductoListResponse response = ProductoListResponse
                .builder()
                .productoDTOList(productoDTOList)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Charge the product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product creation success"),
            @ApiResponse(responseCode = "400", description = "Product bad request"),
            @ApiResponse(responseCode = "500", description = "Product internal service error")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createProducto(@RequestBody ProductoDTO request) {
        productoService.createProducto(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Update the product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product update success"),
            @ApiResponse(responseCode = "400", description = "Product update bad request"),
            @ApiResponse(responseCode = "500", description = "Product Internal service error")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateProducto(@RequestBody ProductoDTO request) {
        productoService.updateProducto(request);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    private List<ProductoDTO> convertToProductos(List<Producto> productoList) {
        var responseList = new ArrayList<ProductoDTO>();
        for (var producto : productoList) {
            var result = modelMapper.map(producto, ProductoDTO.class);
            responseList.add(result);
        }
        return responseList;
    }

}