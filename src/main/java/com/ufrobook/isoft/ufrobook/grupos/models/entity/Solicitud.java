package com.ufrobook.isoft.ufrobook.grupos.models.entity;

import com.ufrobook.isoft.ufrobook.cuenta.models.entity.Usuario;
import com.ufrobook.isoft.ufrobook.grupos.models.enums.EstadoSolicitud;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Table(name = "solicitudes_grupos")
@Getter
@Setter
@Entity
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_solicitud")
    private Date fechaSolicitud;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;


    @ManyToOne
    @JoinColumn(name = "remitente_id")
    private Usuario remitente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Usuario destinatario;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;


    private static final long serialVersionUID = 1L;

}
