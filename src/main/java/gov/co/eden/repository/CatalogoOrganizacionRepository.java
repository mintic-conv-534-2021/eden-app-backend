package gov.co.eden.repository;

import gov.co.eden.entity.CatalogoOrganizacion;
import gov.co.eden.entity.CatalogoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogoOrganizacionRepository extends JpaRepository<CatalogoOrganizacion, Long> {

    List<CatalogoOrganizacion> findAllByActivo(Boolean active);
}
