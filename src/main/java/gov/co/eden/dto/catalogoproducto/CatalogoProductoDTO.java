package gov.co.eden.dto.catalogoproducto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CatalogoProductoDTO {

    private Long catalogoProductoId;
    private String catalogoProductoNombre;
    private Boolean activo;
    private Long catalogoOganizacionId;
}
