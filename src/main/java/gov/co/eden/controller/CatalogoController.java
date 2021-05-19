package gov.co.eden.controller;

import gov.co.eden.dto.catalogo.CatalogoDTO;
import gov.co.eden.dto.catalogo.CatalogoListResponse;
import gov.co.eden.dto.catalogo.CatalogoResponse;
import gov.co.eden.dto.modulo.ModuloDTO;
import gov.co.eden.dto.modulo.ModuloListResponse;
import gov.co.eden.dto.modulo.ModuloResponse;
import gov.co.eden.entity.Catalogo;
import gov.co.eden.entity.Modulo;
import gov.co.eden.service.CatalogoService;
import gov.co.eden.service.ModuloService;
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
import java.util.Arrays;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/catalogo")
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Obtiene el catalogo de acuerdo al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene el catalogo de acuerdo al id",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CatalogoResponse> getCatalogo(@PathVariable("id") long id) {
        Catalogo catalogo = catalogoService.getCatalogoById(id);
        CatalogoDTO catalogoDTO = modelMapper.map(catalogo,CatalogoDTO.class);
        CatalogoResponse response = CatalogoResponse
                .builder()
                .catalogoDTO(catalogoDTO)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Obtiene lista de catalogos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista de catalogos",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CatalogoListResponse> getCatalogoList() {
        List<Catalogo> catalogoList = catalogoService.getAllCatalogo();
        List<CatalogoDTO> catalogoDTOList = convertToCatalogos(catalogoList);
        CatalogoListResponse response = CatalogoListResponse
                .builder()
                .catalogoDTOList(catalogoDTOList)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Charge the catalogue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Catalogue creation success"),
            @ApiResponse(responseCode = "400", description = "Catalogue bad request"),
            @ApiResponse(responseCode = "500", description = "Catalogue internal service error")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCatalogo(@RequestBody CatalogoDTO request) {
        catalogoService.createCatalogo(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Update the catalogue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Catalogue update success"),
            @ApiResponse(responseCode = "400", description = "Catalogue update bad request"),
            @ApiResponse(responseCode = "500", description = "Catalogue Internal service error")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCatalogo(@RequestBody CatalogoDTO request) {
        catalogoService.updateCatalogo(request);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    private List<CatalogoDTO> convertToCatalogos(List<Catalogo> catalogoList) {
        var responseList = new ArrayList<CatalogoDTO>();
        for (var catalogo : catalogoList) {
            var result = modelMapper.map(catalogo, CatalogoDTO.class);
            responseList.add(result);
        }
        return responseList;
    }


}