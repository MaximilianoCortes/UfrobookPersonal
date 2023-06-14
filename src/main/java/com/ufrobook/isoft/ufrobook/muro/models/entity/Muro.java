package com.ufrobook.isoft.ufrobook.muro.models.entity;

import com.ufrobook.isoft.ufrobook.cuenta.models.entity.Usuario;
import com.ufrobook.isoft.ufrobook.tedencias.models.entity.TrendingTopic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "muros")
@Getter
@Setter
@Entity
public class Muro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_foto_portada")
    private Long idFotoPortada;

    @Column(name = "id_foto_perfil")
    private Long idFotoPerfil;

    private String descripcion;

    @Column(name = "lugar_nacimiento")
    private String lugarNacimiento;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "trending_topic_id")
    private TrendingTopic trendingTopic;

    private static final long serialVersionUID = 1L;

}
