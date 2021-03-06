package gov.co.eden.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "PRODUCTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Producto {

    @Id
    @Column(name = "ID_PRODUCTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTO_GENERATOR")
    @SequenceGenerator(name = "PRODUCTO_GENERATOR", sequenceName = "PRODUCTO_SEQUENCE", allocationSize = 1)
    private long idProducto;

    @Basic
    @Column(name = "NOMBRE")
    private String nombre;

    @Basic
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Basic
    @Column(name = "PRECIO")
    private double precio;

    @Basic
    @Column(name = "ACTIVO")
    private boolean activo;

    @Column(name = "URL_IMAGEN")
    private String urlImagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idOrganizacion", referencedColumnName = "ID_ORGANIZACION")
    private Organizacion organizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCatalogoProducto", referencedColumnName = "ID_CATALOGO_PRODUCTO")
    private CatalogoProducto catalogoProducto;

}
