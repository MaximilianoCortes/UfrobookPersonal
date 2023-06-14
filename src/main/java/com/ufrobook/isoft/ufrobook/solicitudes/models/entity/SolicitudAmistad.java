package com.ufrobook.isoft.ufrobook.solicitudes.models.entity;

import com.ufrobook.isoft.ufrobook.cuenta.models.entity.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "solicitudes_amistad")
@Getter
@Setter
@Entity
public class SolicitudAmistad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_remitente_id")
    private Usuario usuarioRemitente;

    @ManyToOne
    @JoinColumn(name = "usuario_destinatario_id")
    private Usuario usuarioDestinatario;

    private static final long serialVersionUID = 1L;

}
