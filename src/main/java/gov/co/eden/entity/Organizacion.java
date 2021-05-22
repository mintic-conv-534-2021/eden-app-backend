package gov.co.eden.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ORGANIZACION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Organizacion {

    @Id
    @Column(name = "ID_ORGANIZACION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZACION_GENERATOR")
    @SequenceGenerator(name = "ORGANIZACION_GENERATOR", sequenceName = "ORGANIZACION_SEQUENCE", allocationSize = 1)
    private long idOrganizacion;

    @JoinColumn(name = "ID_RED_SOCIAL", referencedColumnName = "ID_RED_SOCIAL")
    @OneToOne(fetch = FetchType.LAZY)
    private RedSocial redSocial;

    @Basic
    @Column(name = "NOMBRE")
    private String nombre;

    @Basic
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Basic
    @Column(name = "TELEFONO")
    private String telefono;

    @Basic
    @Column(name = "DIRECCION")
    private String direccion;

    @Basic
    @Column(name = "RM")
    private String rm;

    @Basic
    @Column(name = "RNT")
    private String rnt;

    @Basic
    @Column(name = "RUT")
    private String rut;

    @Basic
    @Column(name = "activo")
    private Boolean activo;

    @Basic
    @Column(name = "EMAIL")
    private String email;

    @Basic
    @Column(name = "URL_LOGO")
    private String urlLogo;

    @Basic
    @Column(name = "URL_BANNER")
    private String urlBanner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCatalogoOrganizacion", referencedColumnName = "ID_CATALOGO_ORGANIZACION")
    private CatalogoOrganizacion catalogoOrganizacion;

}
