package com.ufrobook.isoft.ufrobook.publi_histo.models.entity;

import com.ufrobook.isoft.ufrobook.grupos.models.entity.Grupo;
import com.ufrobook.isoft.ufrobook.muro.models.entity.Muro;
import com.ufrobook.isoft.ufrobook.muro.models.entity.MuroGrupal;
import com.ufrobook.isoft.ufrobook.tedencias.models.entity.HashTag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Table(name = "historias")
@Getter
@Setter
@Entity
public class Historia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagen;

    private String video;

    @NotNull
    @Column(name = "tiempo_historia")
    private int tiempoHistoria;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_historia")
    private Date fechaHistoria;

    @ManyToOne
    @JoinColumn(name = "muro_id")
    private Muro muro;

    @ManyToOne
    @JoinColumn(name = "muro_grupal_id")
    private MuroGrupal muroGrupal;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @ManyToMany
    @JoinTable(name = "historia_hashtags", joinColumns = @JoinColumn(name = "historia_id"), inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private List<HashTag> hashtags;

    private static final long serialVersionUID = 1L;

}
