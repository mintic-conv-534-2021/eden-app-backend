package gov.co.eden.dto.evento;

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
public class EventoDTO {
    private long idEvento;
    private String nombre;
    private Boolean activo;
    private String urlImagenWeb;
    private String urlImagenMovil;
    private String linkExterno;
}
