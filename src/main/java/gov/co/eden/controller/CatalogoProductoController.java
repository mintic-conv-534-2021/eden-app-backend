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
import org.springframework.web.bind.annotation.RequestParam;
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
        catalogoProductoDTO.setCatalogoOganizacionId(catalogoProducto.getCatalogoOrganizacion().getCatalogoOrganizacionId());
        CatalogoProductoResponse response = CatalogoProductoResponse
                .builder()
                .catalogoProductoDTO(catalogoProductoDTO)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Obtiene el catalogo de producto de acuerdo al id del catalogo de organizacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene el catalogo de producto de acuerdo al id del catalogo de organizacion",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoProductoResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping(value = "/catalogo-organizacion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CatalogoProductoListResponse> getCatalogoProductoByCatalogoOrganizacion(@PathVariable("id") long catalogoOrganizacionId) {
        List<CatalogoProducto> catalogoProductoList = catalogoProductoService.getCatalogoByCatalogoOrganizacionId(catalogoOrganizacionId);
        List<CatalogoProductoDTO> catalogoProductoDTOList = convertToCatalogoProductoDTO(catalogoProductoList);
        CatalogoProductoListResponse response = CatalogoProductoListResponse
                .builder()
                .catalogoProductoDTOList(catalogoProductoDTOList)
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
    public ResponseEntity<CatalogoProductoListResponse> getCatalogoProductoList(@RequestParam(value = "activo") Boolean activo){
        List<CatalogoProducto> catalogoProductoList = catalogoProductoService.getAllCatalogo(activo);
        List<CatalogoProductoDTO> catalogoProductoDTOList = convertToCatalogoProductoDTO(catalogoProductoList);
        CatalogoProductoListResponse response = CatalogoProductoListResponse
                .builder()
                .catalogoProductoDTOList(catalogoProductoDTOList)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Carga el catalogo productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Catalogo de productos creado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request de catalogo de la productos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCatalogoProducto(@RequestBody List<CatalogoProductoDTO> request) {
        catalogoProductoService.createCatalogo(convertToCatalogoProductoEntity(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Actualiza el estado del catalogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Estado del catalogo actulizado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request del estado del catalogo"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(value = "/activo")
    public ResponseEntity<Void> changeStateCatalogo(@RequestParam(value = "catalogoProductoId") Long catalogoProductoId,
                                                    @RequestParam(value = "activo") Boolean active) {
        catalogoProductoService.changeCatalogoState(catalogoProductoId, active);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    @Operation(summary = "Actualiza el catalogo productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Catalogo de productos actualizado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request de catalogo de la productos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCatalogoProducto(@RequestBody CatalogoProductoDTO request) {
        catalogoProductoService.updateCatalogo(request);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    private List<CatalogoProductoDTO> convertToCatalogoProductoDTO(List<CatalogoProducto> catalogoProductoList) {
        var responseList = new ArrayList<CatalogoProductoDTO>();
        for (var catalogo : catalogoProductoList) {
            var result = modelMapper.map(catalogo, CatalogoProductoDTO.class);
            result.setCatalogoOganizacionId(catalogo.getCatalogoOrganizacion().getCatalogoOrganizacionId());
            responseList.add(result);
        }
        return responseList;
    }

    private List<CatalogoProducto> convertToCatalogoProductoEntity(List<CatalogoProductoDTO> catalogoProductoDTOList) {
        var entityList = new ArrayList<CatalogoProducto>();
        for (var catalogo : catalogoProductoDTOList) {
            var result = modelMapper.map(catalogo, CatalogoProducto.class);
            result.setCatalogoOrganizacion(CatalogoOrganizacion.builder().
                    catalogoOrganizacionId(catalogo.getCatalogoOganizacionId()).build());
            entityList.add(result);
        }
        return entityList;
    }
}