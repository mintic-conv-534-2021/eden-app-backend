package gov.co.eden.repository;

import gov.co.eden.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findAllByCatalogoProducto_IdCatalogoProducto(Long idCatalogoProducto);

}
