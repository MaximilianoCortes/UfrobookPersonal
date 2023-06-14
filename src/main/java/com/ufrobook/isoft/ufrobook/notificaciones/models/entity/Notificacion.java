package com.ufrobook.isoft.ufrobook.notificaciones.models.entity;

import com.ufrobook.isoft.ufrobook.cuenta.models.entity.Usuario;
import com.ufrobook.isoft.ufrobook.notificaciones.models.enums.TipoNotificacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Table(name = "notificaciones")
@Getter
@Setter
@Entity
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @NotBlank
    private String contenido;

    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private static final long serialVersionUID = 1L;

}
