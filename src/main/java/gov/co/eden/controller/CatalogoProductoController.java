package gov.co.eden.controller;

import gov.co.eden.dto.catalogoproducto.CatalogoProductoDTO;
import gov.co.eden.dto.catalogoproducto.CatalogoProductoListResponse;
import gov.co.eden.dto.catalogoproducto.CatalogoProductoResponse;
import gov.co.eden.entity.CatalogoOrganizacion;
import gov.co.eden.entity.CatalogoProducto;
import gov.co.eden.service.CatalogoProductoService;
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
@RequestMapping("/api/v1/catalogo-producto")
public class CatalogoProductoController {

    @Autowired
    private CatalogoProductoService catalogoProductoService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Obtiene el catalogo de producto de acuerdo al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene el catalogo de producto de acuerdo al id",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoProductoResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CatalogoProductoResponse> getCatalogoProducto(@PathVariable("id") long id) {
        CatalogoProducto catalogoProducto = catalogoProductoService.getCatalogoById(id);
        CatalogoProductoDTO catalogoProductoDTO = modelMapper.map(catalogoProducto, CatalogoProductoDTO.class);
        CatalogoProductoResponse response = CatalogoProductoResponse
                .builder()
                .catalogoProductoDTO(catalogoProductoDTO)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Obtiene lista de catalogo de producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista de catalogo de producto",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoProductoListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CatalogoProductoListResponse> getCatalogoProductoList() {
        List<CatalogoProducto> catalogoProductoList = catalogoProductoService.getAllCatalogo();
        List<CatalogoProductoDTO> catalogoProductoDTOList = convertToCatalogoProducto(catalogoProductoList);
        CatalogoProductoListResponse response = CatalogoProductoListResponse
                .builder()
                .catalogoProductoDTOList(catalogoProductoDTOList)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Carga el catalogo productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Catalogo de productos creado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request de catalogo de la productos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCatalogoProducto(@RequestBody CatalogoProductoDTO request) {
        catalogoProductoService.createCatalogo(modelMapper.map(request, CatalogoProducto.class));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Actualiza el catalogo productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Catalogo de productos actualizado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request de catalogo de la productos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCatalogoProducto(@RequestBody CatalogoProductoDTO request) {
        catalogoProductoService.updateCatalogo(modelMapper.map(request, CatalogoProducto.class));
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    private List<CatalogoProductoDTO> convertToCatalogoProducto(List<CatalogoProducto> catalogoProductoList) {
        var responseList = new ArrayList<CatalogoProductoDTO>();
        for (var catalogo : catalogoProductoList) {
            var result = modelMapper.map(catalogo, CatalogoProductoDTO.class);
            responseList.add(result);
        }
        return responseList;
    }


}