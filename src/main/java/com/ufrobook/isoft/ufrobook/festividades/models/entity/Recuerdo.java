package com.ufrobook.isoft.ufrobook.festividades.models.entity;

import com.ufrobook.isoft.ufrobook.publi_histo.models.entity.Publicacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "recuerdos")
public class Recuerdo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "tiempo_transcurrido")
    private Date tiempoTranscurrido;

    @NotNull
    private String contenido;

    @NotNull
    private int duracion;

    @NotNull
    private int repeticion;

    @OneToOne
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;

    private static final long serialVersionUID = 1L;

}
