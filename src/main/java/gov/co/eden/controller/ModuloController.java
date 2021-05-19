package gov.co.eden.controller;

import gov.co.eden.dto.modulo.ModuloDTO;
import gov.co.eden.dto.modulo.ModuloListResponse;
import gov.co.eden.dto.modulo.ModuloResponse;
import gov.co.eden.entity.Modulo;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/modulo")
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Obtiene el modulo de acuerdo al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene el modulo de acuerdo al id",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ModuloResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ModuloResponse> getModulo(@PathVariable("id") long id) {
        Modulo modulo = moduloService.getModuloById(id);
        ModuloDTO moduloDTO = modelMapper.map(modulo, ModuloDTO.class);
        ModuloResponse response = ModuloResponse
                .builder()
                .moduloDTO(moduloDTO)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Obtiene lista de modulos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista de modulos",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ModuloListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ModuloListResponse> getModuloList() {
        List<Modulo> moduloList = moduloService.getAllModulo();
        List<ModuloDTO> moduloDTOList = convertToModulos(moduloList);
        ModuloListResponse response = ModuloListResponse
                .builder()
                .moduloDTOList(moduloDTOList)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Charge the module")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Modulo creation success"),
            @ApiResponse(responseCode = "400", description = "Modulo budget bad request"),
            @ApiResponse(responseCode = "500", description = "Modulo internal service error")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createModulo(@RequestBody ModuloDTO request) {
        moduloService.createModulo(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Update the module")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Module update success"),
            @ApiResponse(responseCode = "400", description = "Module update bad request"),
            @ApiResponse(responseCode = "500", description = "Internal service error")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateModulo(@RequestBody ModuloDTO request) {
        moduloService.updateModulo(request);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    private List<ModuloDTO> convertToModulos(List<Modulo> moduloList) {
        var responseList = new ArrayList<ModuloDTO>();
        for (var modulo : moduloList) {
            var result = modelMapper.map(modulo, ModuloDTO.class);
            responseList.add(result);
        }
        return responseList;
    }

}