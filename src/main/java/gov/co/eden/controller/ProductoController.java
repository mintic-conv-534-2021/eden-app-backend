package gov.co.eden.controller;

import gov.co.eden.dto.catalogoproducto.CatalogoProductoListResponse;
import gov.co.eden.dto.catalogoproducto.CatalogoProductoResponse;
import gov.co.eden.dto.organizacion.OrganizacionDTO;
import gov.co.eden.dto.organizacion.OrganizacionListResponse;
import gov.co.eden.dto.producto.ProductoDTO;
import gov.co.eden.dto.producto.ProductoListResponse;
import gov.co.eden.dto.producto.ProductoResponse;
import gov.co.eden.entity.Organizacion;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
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
                            schema = @Schema(implementation = ProductoResponse.class))}),
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
                            schema = @Schema(implementation = ProductoListResponse.class))}),
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

    @Operation(summary = "Obtiene lista de productos por id categoria de productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista de productos por id categoria de productos",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductoListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(value = "/catalogo-organizacion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductoListResponse> getProductoListByCatalogoProductoId
            (@PathVariable(value = "id") Long catalogoProductoId) {
        List<Producto> productoList = productoService.getProductoByCatalogoProductoId(catalogoProductoId);
        List<ProductoDTO> productoDTOList = convertToProductos(productoList);
        ProductoListResponse response = ProductoListResponse
                .builder()
                .productoDTOList(productoDTOList)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Carga los datos del producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Producto creada satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request de producto"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> createProducto(@RequestPart ProductoDTO request, @RequestPart("imagen") MultipartFile imagen) throws IOException {
        productoService.createProducto(request,imagen);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Actualiza los datos del producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Producto actulizado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request de producto"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateProducto(@RequestBody ProductoDTO request) {
        productoService.updateProducto(request);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    @Operation(summary = "Actualiza el estado del producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Estado del producto actulizado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request del estado del producto"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(value = "/activo",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeStateProducto(@RequestParam(value = "productoId") Long productoId,
                                                    @RequestParam(value = "activo") Boolean active) {
        productoService.changeProductoState(productoId,active);
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