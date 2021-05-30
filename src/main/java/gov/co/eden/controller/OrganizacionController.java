package gov.co.eden.controller;

import gov.co.eden.dto.organizacion.OrganizacionDTO;
import gov.co.eden.dto.organizacion.OrganizacionListResponse;
import gov.co.eden.dto.organizacion.OrganizacionResponse;
import gov.co.eden.entity.Organizacion;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
                            schema = @Schema(implementation = OrganizacionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizacionResponse> getOrganizacion(@PathVariable("id") long id) {
        Organizacion organizacion = organizacionService.getOrganizacionById(id);
        OrganizacionDTO organizacionDTO = modelMapper.map(organizacion, OrganizacionDTO.class);
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
                            schema = @Schema(implementation = OrganizacionListResponse.class))}),
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

    @Operation(summary = "Obtiene lista de organizaciones por id categoria de organizacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista de organizaciones id por categoria de organizacion",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrganizacionListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(value = "/catalogo-organizacion/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizacionListResponse> getOrganizacionListByCatalogoOrganizacionId
            (@PathVariable(value = "id") Long catalogoOrganizacionId) {
        List<Organizacion> catalogoList = organizacionService.getOrganizacionByCatalogoOrganizacionId(catalogoOrganizacionId);
        List<OrganizacionDTO> catalogoDTOList = convertToOrganizacion(catalogoList);
        OrganizacionListResponse response = OrganizacionListResponse
                .builder()
                .organizacionDTOList(catalogoDTOList)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Obtiene mapa de organizaciones por catalogo de organizacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene mapa de organizaciones or catalogo de organizacion",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrganizacionListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(value = "/catalogo-organizacion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizacionListResponse> getOrganizacionListByCatalogoProducto() {
        Map<Long, List<Organizacion>> catalogoList = organizacionService.findOrganizationByCatalogoOrganization();
        Map<Long, List<OrganizacionDTO>> catalogoDTOList = convertToOrganizacionMap(catalogoList);
        OrganizacionListResponse response = OrganizacionListResponse
                .builder()
                .organizationListByCatalogoOrganizacion(catalogoDTOList)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Carga los datos de la organizacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Organizacion creada satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request de organizacion"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> createOrganizacion(@RequestPart("request") OrganizacionDTO request,
                                                   @RequestPart("logo") MultipartFile logo,
                                                   @RequestPart("banner") MultipartFile banner,
                                                   @RequestPart("rm") MultipartFile rm,
                                                   @RequestPart("rut") MultipartFile rut,
                                                   @RequestPart("rnt") MultipartFile rnt) throws IOException {
        organizacionService.createOrganizacion(request,logo,banner,rm,rut,rnt);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Actualiza los datos de la organizacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Organizacion actualizada satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request de organizacion"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateOrganizacion(@RequestBody OrganizacionDTO request) {
        organizacionService.updateOrganizacion(request);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    @Operation(summary = "Actualiza el estado de la organizacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estado de la organizacion actulizado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request del estado de la organizacion"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(value = "/activo",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeStateOrganizacion(@RequestParam(value = "organizacionId") Long organizacionId,
                                                    @RequestParam(value = "activo") Boolean active) {
        organizacionService.changeOrganizationState(organizacionId,active);
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

    private Map<Long, List<OrganizacionDTO>> convertToOrganizacionMap(Map<Long, List<Organizacion>> organizacionList) {
        Map<Long, List<OrganizacionDTO>> dtos2 = new HashMap<>();
        for (Map.Entry<Long, List<Organizacion>> mapByDate : organizacionList.entrySet()) {
            List<OrganizacionDTO> organizacionDTOList = new ArrayList<>();
            for (Organizacion entity : mapByDate.getValue()) {
                OrganizacionDTO dto = modelMapper.map(entity, OrganizacionDTO.class);
                organizacionDTOList.add(dto);
            }
            dtos2.put(mapByDate.getKey(), organizacionDTOList);
        }
        return dtos2;
    }

}