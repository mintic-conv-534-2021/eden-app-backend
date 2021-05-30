package gov.co.eden.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CATALOGO_ORGANIZACION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CatalogoOrganizacion {

    @Id
    @Column(name = "ID_CATALOGO_ORGANIZACION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATALOGO_ORGANIZACION_GENERATOR")
    @SequenceGenerator(name = "CATALOGO_ORGANIZACION_GENERATOR", sequenceName = "CATALOGO_ORGANIZACION_SEQUENCE", allocationSize = 1)
    private long catalogoOrganizacionId;

    @Basic
    @Column(name = "NOMBRE")
    private String nombre;

    @Basic
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Basic
    @Column(name = "URL_IMAGEN")
    private String urlImagen;

    @Basic
    @Column(name = "ACTIVO")
    private boolean activo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "catalogoOrganizacion")
    private List<Organizacion> organizacionList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "catalogoOrganizacion")
    private List<CatalogoProducto> categoriaProductoList;

}
