package gov.co.eden.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MODULO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Modulo {

    @Id
    @Column(name = "ID_MODULO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MODULO_GENERATOR")
    @SequenceGenerator(name = "MODULO_GENERATOR", sequenceName = "MODULO_SEQUENCE", allocationSize = 1)
    private long idModulo;

    @Basic
    @Column(name = "NOMBRE")
    private String nombre;

    @Basic
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "modulo")
    private List<Catalogo> catalogos;

}
