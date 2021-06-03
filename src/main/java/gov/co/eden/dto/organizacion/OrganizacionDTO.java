package gov.co.eden.dto.organizacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import gov.co.eden.dto.producto.ProductoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizacionDTO {

    private Long organizacionId;
    private String nombre;
    private String descripcion;
    private String telefono;
    private String direccion;
    private String email;
    private String rm;
    private String rut;
    private String rnt;
    private Boolean activo;
    private String urlLogo;
    private String urlBanner;
    private String urlRM;
    private String urlRNT;
    private String urlRUT;
    private RedSocialDTO redSocial;
    private Long catalogoOrganizacionId;
    private List<ProductoDTO> productoList;
    private Map<String, List<ProductoDTO>> productosByCatalogoProductoMap;
}
