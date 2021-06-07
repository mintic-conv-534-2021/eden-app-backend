package gov.co.eden.service.impl;

import gov.co.eden.dto.evento.EventoDTO;
import gov.co.eden.entity.Evento;
import gov.co.eden.exception.NotFoundException;
import gov.co.eden.repository.EventoRepository;
import gov.co.eden.service.AWSS3Service;
import gov.co.eden.service.EventoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private AWSS3Service aws3Service;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final static String EVENTO_PATH = "evento";

    @Override
    public Evento getEventoById(long eventoId) {
        Optional<Evento> catalogo = eventoRepository.findById(eventoId);
        if (!catalogo.isPresent())
            throw new NotFoundException("No evento found on database for id: " + eventoId);
        return catalogo.get();
    }

    @Override
    public List<Evento> getAllEventos(Boolean activo) {
        List<Evento> entities = eventoRepository.findAllByActivo(activo);
        log.info("Found {} of evento", entities.size());
        if (entities.isEmpty())
            throw new NotFoundException("No eventos found on database");
        return entities;
    }

    @Override
    public void createEvento(EventoDTO request, MultipartFile imagenWeb, MultipartFile imagenMovil) throws IOException {
        Evento evento = modelMapper.map(request, Evento.class);
        evento.setUrlImagenMovil(aws3Service.uploadFile(imagenMovil,EVENTO_PATH));
        evento.setUrlImagenWeb(aws3Service.uploadFile(imagenWeb,EVENTO_PATH));
        evento.setActivo(true);
        eventoRepository.save(evento);
    }

    @Override
    public void updateEvento(EventoDTO request, MultipartFile imagenWeb, MultipartFile imagenMovil) throws IOException {

        Evento evento = eventoRepository.findById(request.getIdEvento()).
                orElseThrow(() -> new NotFoundException("Evento con id "
                        + request.getIdEvento() + " no existe en la BD"));
        merge(request, evento);
        if (imagenWeb != null) {
            aws3Service.deleteObject(evento.getUrlImagenWeb());
            evento.setUrlImagenWeb(aws3Service.uploadFile(imagenWeb, EVENTO_PATH));
        }
        if (imagenMovil != null) {
            aws3Service.deleteObject(evento.getUrlImagenMovil());
            evento.setUrlImagenMovil(aws3Service.uploadFile(imagenMovil, EVENTO_PATH));
        }
        eventoRepository.save(evento);
    }

    @Override
    public void changeEventoState(long eventoId, Boolean estado) {
        Evento evento = eventoRepository.findById(eventoId).
                orElseThrow(() -> new NotFoundException("Evento con id "
                        + eventoId + " no existe en la BD"));
        evento.setActivo(estado);
        eventoRepository.save(evento);
    }

    public static <T> void merge(T source, T target) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(source, target);
    }

}
