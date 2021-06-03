package gov.co.eden.dto.producto;

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
public class ProductoDTO {
    private Long productoId;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Boolean activo;
    private String urlImagen;
    private Long organizacionId;
    private Long catalogoProductoId;
    private String catalogoProductoNombre;
}
