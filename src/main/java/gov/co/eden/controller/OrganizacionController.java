package gov.co.eden.controller;

import gov.co.eden.dto.catalogo.CatalogoDTO;
import gov.co.eden.dto.catalogo.CatalogoListResponse;
import gov.co.eden.dto.catalogo.CatalogoResponse;
import gov.co.eden.dto.organizacion.OrganizacionDTO;
import gov.co.eden.dto.organizacion.OrganizacionListResponse;
import gov.co.eden.dto.organizacion.OrganizacionResponse;
import gov.co.eden.entity.Catalogo;
import gov.co.eden.entity.Organizacion;
import gov.co.eden.service.CatalogoService;
import gov.co.eden.service.OrganizacionService;
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
@RequestMapping("/api/v1/organizacion")
public class OrganizacionController {

    @Autowired
    private OrganizacionService organizacionService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Obtiene el organizacion de acuerdo al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene el organizacion de acuerdo al id",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizacionResponse> getOrganizacion(@PathVariable("id") long id) {
        Organizacion organizacion = organizacionService.getOrganizacionById(id);
        OrganizacionDTO organizacionDTO = modelMapper.map(organizacion,OrganizacionDTO.class);
        OrganizacionResponse response = OrganizacionResponse
                .builder()
                .organizacionDTO(organizacionDTO)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Obtiene lista de organizaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista de organizaciones",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizacionListResponse> getOrganizacionList() {
        List<Organizacion> catalogoList = organizacionService.getAllOrganizacion();
        List<OrganizacionDTO> catalogoDTOList = convertToOrganizacion(catalogoList);
        OrganizacionListResponse response = OrganizacionListResponse
                .builder()
                .organizacionDTOList(catalogoDTOList)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Charge the organizacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Organizacion creation success"),
            @ApiResponse(responseCode = "400", description = "Organizacion bad request"),
            @ApiResponse(responseCode = "500", description = "Organizacion internal service error")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrganizacion(@RequestBody OrganizacionDTO request) {
        organizacionService.createOrganizacion(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Update the organizacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Organizacion update success"),
            @ApiResponse(responseCode = "400", description = "Organizacion update bad request"),
            @ApiResponse(responseCode = "500", description = "Organizacion Internal service error")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateOrganizacion(@RequestBody OrganizacionDTO request) {
        organizacionService.updateOrganizacion(request);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    private List<OrganizacionDTO> convertToOrganizacion(List<Organizacion> organizacionList) {
        var responseList = new ArrayList<OrganizacionDTO>();
        for (var organizacion : organizacionList) {
            var result = modelMapper.map(organizacion, OrganizacionDTO.class);
            responseList.add(result);
        }
        return responseList;
    }

}