package gov.co.eden.service;

import gov.co.eden.dto.evento.EventoDTO;
import gov.co.eden.entity.Evento;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventoService {

    Evento getEventoById(long eventoId);

    List<Evento> getAllEventos(Boolean activo);

    void createEvento(EventoDTO request, MultipartFile imagenWeb, MultipartFile imagenMovil) throws IOException;

    void updateEvento(EventoDTO request, MultipartFile imagenWeb, MultipartFile imagenMovil) throws IOException;

    void changeEventoState(long eventoId, Boolean estado);
}
