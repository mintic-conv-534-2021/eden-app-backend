package gov.co.eden.dto.organizacion;

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
public class RedSocialDTO {

    private String urlFacebook;
    private String urlTwitter;
    private String whatsapp;
    private String urlInstagram;
    private String urlTiktok;
    private String webPage;

}
