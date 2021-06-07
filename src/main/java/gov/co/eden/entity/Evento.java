package gov.co.eden.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Builder
@Entity
@Table(name = "EVENTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Evento {

    @Id
    @Column(name = "ID_EVENTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENTO_GENERATOR")
    @SequenceGenerator(name = "EVENTO_GENERATOR", sequenceName = "EVENTO_SEQUENCE", allocationSize = 1)
    private long idEvento;

    @Basic
    @Column(name = "NOMBRE")
    private String nombre;

    @Basic
    @Column(name = "ACTIVO")
    private boolean activo;

    @Basic
    @Column(name = "URL_IMAGEN_WEB")
    private String urlImagenWeb;

    @Basic
    @Column(name = "URL_IMAGEN_MOVIL")
    private String urlImagenMovil;

    @Basic
    @Column(name = "LINK_EXTERNO")
    private String linkExterno;

}
