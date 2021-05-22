package gov.co.eden.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RED_SOCIAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RedSocial {

    @Id
    @Column(name = "ID_RED_SOCIAL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RED_SOCIAL_GENERATOR")
    @SequenceGenerator(name = "RED_SOCIAL_GENERATOR", sequenceName = "RED_SOCIAL_SEQUENCE", allocationSize = 1)
    private long idRedSocial;
    
    @Basic
    @Column(name = "URL_FACEBOOK")
    private String urlFacebook;

    @Basic
    @Column(name = "URL_TWITTER")
    private String urlTwitter;

    @Basic
    @Column(name = "WHATSAPP")
    private String whatsapp;

    @Basic
    @Column(name = "URL_INSTAGRAM")
    private String urlInstagram;

    @Basic
    @Column(name = "URL_TIKTOK")
    private String urlTiktok;

    @Basic
    @Column(name = "WEB_PAGE")
    private String webPage;

}
