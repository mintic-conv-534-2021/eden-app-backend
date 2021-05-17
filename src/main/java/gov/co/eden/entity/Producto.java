package gov.co.eden.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Column(name = "CANTIDAD")
    private int cantidad;

    @Basic
    @Column(name = "PRECIO")
    private double precio;

    @Basic
    @Column(name = "ESTADO")
    private boolean estado;

    @Column(name = "IMAGEN")
    private String imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGANIZACION")
    private Organizacion organizacion;

}
