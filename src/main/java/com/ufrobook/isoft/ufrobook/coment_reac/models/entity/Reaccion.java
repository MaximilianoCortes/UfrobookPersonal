package com.ufrobook.isoft.ufrobook.coment_reac.models.entity;

import com.ufrobook.isoft.ufrobook.cuenta.models.entity.Usuario;
import com.ufrobook.isoft.ufrobook.publi_histo.models.entity.Historia;
import com.ufrobook.isoft.ufrobook.publi_histo.models.entity.Publicacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Table(name = "reacciones")
@Entity
public class Reaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String tipo;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecha;


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "comentario_id")
    private Comentario comentario;

    @ManyToOne
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "historia_id")
    private Historia historia;

    private static final long serialVersionUID = 1L;

}
