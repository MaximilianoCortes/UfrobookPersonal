package com.ufrobook.isoft.ufrobook.coment_reac.models.entity;

import com.ufrobook.isoft.ufrobook.cuenta.models.entity.Usuario;
import com.ufrobook.isoft.ufrobook.publi_histo.models.entity.Publicacion;
import com.ufrobook.isoft.ufrobook.tedencias.models.entity.HashTag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table(name = "comentarios")
@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @NotBlank
    private String texto;

    @ManyToOne
    @JoinColumn(name = "comentario_padre_id")
    private Comentario comentarioPadre;

    /**
     * Dueño de la entidad debe revisar posible recursividad y avisar a integracion
     * que se queda finalmente
     */
    // @OneToMany(mappedBy = "comentarioPadre", cascade = CascadeType.ALL)
    // private List<Comentario> respuestas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;

    @ManyToMany
    @JoinTable(name = "comentario_hashtags", joinColumns = @JoinColumn(name = "comentario_id"), inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private List<HashTag> hashtags;

    private static final long serialVersionUID = 1L;
}
