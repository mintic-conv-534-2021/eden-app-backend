package gov.co.eden.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "CATALOGO_PRODUCTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CatalogoProducto {

    @Id
    @Column(name = "ID_CATALOGO_PRODUCTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATALOGO_PRODUCTO_GENERATOR")
    @SequenceGenerator(name = "CATALOGO_PRODUCTO_GENERATOR", sequenceName = "CATALOGO_PRODUCTO_SEQUENCE", allocationSize = 1)
    private long idCatalogoProducto;

    @Basic
    @Column(name = "NOMBRE")
    private String nombre;

    @Basic
    @Column(name = "ACTIVO")
    private boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCatalogoOrganizacion", referencedColumnName = "ID_CATALOGO_ORGANIZACION")
    private CatalogoOrganizacion catalogoOrganizacion;

    @OneToMany(
            mappedBy = "catalogoProducto",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Producto> productoList;

}
