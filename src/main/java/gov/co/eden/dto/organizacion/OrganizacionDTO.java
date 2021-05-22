package gov.co.eden.dto.organizacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.List;

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
    private Boolean activo;
    private String urlLogo;
    private String urlBanner;
    private String rm;
    private String rnt;
    private String rut;
    private RedSocialDTO redSocialDTO;
    private List<Long> productosIdList;
    private Long catalogoOrganizacionId;
}
