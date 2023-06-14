package com.ufrobook.isoft.ufrobook.mensajes.models.entity;

import com.ufrobook.isoft.ufrobook.cuenta.models.entity.Usuario;
import com.ufrobook.isoft.ufrobook.grupos.models.entity.Grupo;
import com.ufrobook.isoft.ufrobook.mensajes.models.enums.EstadoMensaje;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Table(name = "mensajes")
@Getter
@Setter
@Entity
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String contenido;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_envio")
    private Date fechaEnvio;

    @Enumerated(EnumType.STRING)
    private EstadoMensaje estado;

    @ManyToOne
    @JoinColumn(name = "chat_grupal_id")
    private ChatGrupal chatGrupal;

    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private Usuario emisor;

    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private Usuario receptor;

    private static final long serialVersionUID = 1L;

}
