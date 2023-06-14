package com.ufrobook.isoft.ufrobook.festividades.models.entity;

import com.ufrobook.isoft.ufrobook.publi_histo.models.entity.Publicacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Table(name = "festividades")
@Getter
@Setter
@Entity
public class Festividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nombre_festividad")
    private String nombreFestividad;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @NotNull
    private int duracion;

    @OneToOne
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;

    private static final long serialVersionUID = 1L;

}
