package gov.co.eden.repository;

import gov.co.eden.entity.Organizacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {

    List<Organizacion> findAllByCatalogoOrganizacion_IdCatalogoOrganizacion(Long idCatalogoOrganizacion);

}