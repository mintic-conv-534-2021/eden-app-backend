package gov.co.eden.controller;

import gov.co.eden.dto.evento.EventoDTO;
import gov.co.eden.dto.evento.EventoListResponse;
import gov.co.eden.dto.evento.EventoResponse;
import gov.co.eden.entity.Evento;
import gov.co.eden.service.EventoService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Obtiene el evento de acuerdo al id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene el evento de acuerdo al id",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EventoResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoResponse> getEvento(@PathVariable("id") long id) {
        Evento evento = eventoService.getEventoById(id);
        EventoDTO eventoDTO = modelMapper.map(evento, EventoDTO.class);
        EventoResponse response = EventoResponse
                .builder()
                .eventoDTO(eventoDTO)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Obtiene lista de eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene lista de eventos",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EventoListResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del sistema")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventoListResponse> getEventoList(@RequestParam(value = "activo") Boolean active) {
        List<Evento> eventoList = eventoService.getAllEventos(active);
        List<EventoDTO> eventoDTOList = convertToEventos(eventoList);
        EventoListResponse response = EventoListResponse
                .builder()
                .eventoDTOList(eventoDTOList)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @Operation(summary = "Carga los datos del evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Evento creado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request de evento"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> createEvento(@RequestPart EventoDTO request,
                                             @RequestPart("imagen") MultipartFile imagenWeb,
                                             @RequestPart("imagen") MultipartFile imagenMovil) throws IOException {
        eventoService.createEvento(request, imagenWeb, imagenMovil);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Actualiza los datos del evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Evento actulizado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request de evento"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Void> updateEvento(@RequestPart EventoDTO request,
                                             @RequestPart(name = "imagen", required = false) MultipartFile imagenWeb,
                                             @RequestPart(name = "imagen", required = false) MultipartFile imagenMovil) throws IOException {
        eventoService.updateEvento(request, imagenWeb, imagenMovil);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    @Operation(summary = "Actualiza el estado del evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Estado del evento actulizado satisfactoriamente"),
            @ApiResponse(responseCode = "400", description = "Error en el request del estado del evento"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping(value = "/activo")
    public ResponseEntity<Void> changeStateEvento(@RequestParam(value = "eventoId") Long eventoId,
                                                  @RequestParam(value = "activo") Boolean active) {
        eventoService.changeEventoState(eventoId, active);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    private List<EventoDTO> convertToEventos(List<Evento> eventoList) {
        var responseList = new ArrayList<EventoDTO>();
        for (var evento : eventoList) {
            var result = modelMapper.map(evento, EventoDTO.class);
            responseList.add(result);
        }
        return responseList;
    }

}