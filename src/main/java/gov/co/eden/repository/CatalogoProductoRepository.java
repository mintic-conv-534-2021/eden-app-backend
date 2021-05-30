package gov.co.eden.repository;

import gov.co.eden.entity.CatalogoProducto;
import gov.co.eden.entity.Organizacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogoProductoRepository extends JpaRepository<CatalogoProducto, Long> {

    List<CatalogoProducto> findAllByCatalogoOrganizacion_CatalogoOrganizacionId(Long catalogoOrganizacionId);
}
