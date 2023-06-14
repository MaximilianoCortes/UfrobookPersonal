package com.ufrobook.isoft.ufrobook.muro.models.entity;

import com.ufrobook.isoft.ufrobook.grupos.models.entity.Grupo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "muros_grupales")
@Getter
@Setter
@Entity
public class MuroGrupal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_foto_grupo")
    private Long idFotoGrupo;

    @Column(name = "id_foto_portada")
    private Long idFotoPortada;

    @OneToOne
    private Grupo grupo;

    private static final long serialVersionUID = 1L;

}