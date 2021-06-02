package gov.co.eden.controller;

import gov.co.eden.dto.catalogoorganizacion.CatalogoOrganizacionDTO;
import gov.co.eden.dto.catalogoorganizacion.CatalogoOrganizacionListResponse;
import gov.co.eden.dto.catalogoorganizacion.CatalogoOrganizacionResponse;
import gov.co.eden.entity.CatalogoOrganizacion;
import gov.co.eden.service.CatalogoOrganizacionService;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/catalogo-organizacion")
public class CatalogoOrganizacionController {

    @Autowired
    private CatalogoOrganizacionService catalogoOrganizacionService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Obtiene el catalogo de la organizacion de acuerdo al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene el catalogo de la organizacion de acuerdo al id",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoOrganizacionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CatalogoOrganizacionResponse> getCatalogoOrganizacion(@PathVariable("id") long id) {
        CatalogoOrganizacion catalogoOrganizacion = catalogoOrganizacionService.getCatalogoOrganizacionById(id);
        CatalogoOrganizacionDTO catalogoOrganizacionDTO = modelMapper.map(catalogoOrganizacion, CatalogoOrganizacionDTO.class);
        CatalogoOrganizacionResponse response = CatalogoOrganizacionResponse
                .builder()
                .catalogoOrganizacionDTO(catalogoOrganizacionDTO)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Obtiene lista de catalogo de la organizacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista de catalogo de la organizacion",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoOrganizacionListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CatalogoOrganizacionListResponse> getCatalogoOrganizacionList() {
        List<CatalogoOrganizacion> catalogoOrganizacionList = catalogoOrganizacionService.getAllCatalogoOrganizacion();
        List<CatalogoOrganizacionDTO> catalogoOrganizacionDTOList = convertToCatalogoOrganizacionDTO(catalogoOrganizacionList);
        CatalogoOrganizacionListResponse response = CatalogoOrganizacionListResponse
                .builder()
                .catalogoOrganizacionDTOList(catalogoOrganizacionDTOList)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Carga el catalogo de la organizacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Catalogo de la organizacion creado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request de catalogo de la organizacion"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> createCatalogoOrganizacion(@RequestPart("request") CatalogoOrganizacionDTO request,
                                                           @RequestPart("imagen") MultipartFile imagen) throws IOException {
        catalogoOrganizacionService.createCatalogoOrganizacion(modelMapper.map(request, CatalogoOrganizacion.class), imagen);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Actualiza el catalogo de la organizacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Catalogo de la organizacion actualizado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request de catalogo de la organizacion"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> updateCatalogoOrganizacion(@RequestPart("request") CatalogoOrganizacionDTO request,
                                                           @RequestPart("imagen") MultipartFile imagen) throws IOException {
        catalogoOrganizacionService.updateCatalogoOrganizacion(request ,imagen);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    private List<CatalogoOrganizacionDTO> convertToCatalogoOrganizacionDTO(List<CatalogoOrganizacion> catalogoOrganizacionList) {
        var responseList = new ArrayList<CatalogoOrganizacionDTO>();
        for (var modulo : catalogoOrganizacionList) {
            var result = modelMapper.map(modulo, CatalogoOrganizacionDTO.class);
            responseList.add(result);
        }
        return responseList;
    }

}