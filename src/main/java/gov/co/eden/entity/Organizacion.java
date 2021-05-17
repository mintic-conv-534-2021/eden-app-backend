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
    @Column(name = "EMAIL")
    private String email;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "ID_PRODUCTO")
    private List<Producto> productos;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "ID_CATALOGO")
    private List<Catalogo> catalogos;

}
