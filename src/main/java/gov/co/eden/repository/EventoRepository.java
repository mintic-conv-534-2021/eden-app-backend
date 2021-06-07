package gov.co.eden.repository;

import gov.co.eden.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findAllByActivo(Boolean active);

}
