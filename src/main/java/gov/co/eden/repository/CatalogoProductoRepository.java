package gov.co.eden.repository;

import gov.co.eden.entity.CatalogoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogoProductoRepository extends JpaRepository<CatalogoProducto, Long> {
}
