package gov.co.eden.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CATALOGO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Catalogo {

    @Id
    @Column(name = "ID_CATALOGO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATALOGO_GENERATOR")
    @SequenceGenerator(name = "CATALOGO_GENERATOR", sequenceName = "CATALOGO_SEQUENCE", allocationSize = 1)
    private long idCatalogo;

    @Basic
    @Column(name = "NOMBRE")
    private String nombre;

    @Basic
    @Column(name = "FECHA_INICIO")
    private LocalDate fechaInicio;

    @Basic
    @Column(name = "FECHA_FIN")
    private LocalDate fechaFin;

    @Basic
    @Column(name = "ACTIVO")
    private Boolean activo;

    @Column(name = "IMAGEN")
    private String imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idOrganizacion", referencedColumnName = "ID_ORGANIZACION")
    private Organizacion organizacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idModulo", referencedColumnName = "ID_MODULO")
    private Modulo modulo;

}
