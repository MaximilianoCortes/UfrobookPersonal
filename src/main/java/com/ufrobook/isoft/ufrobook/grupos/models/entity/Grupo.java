package com.ufrobook.isoft.ufrobook.grupos.models.entity;

import com.ufrobook.isoft.ufrobook.cuenta.models.entity.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "grupos")
@Getter
@Setter
@Entity
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_grupo")
    private String nombreGrupo;

    private String descripcion;

    @Column(name = "foto_grupo")
    private String foto;

    @Column(name = "cant_maximo")
    private int cantMaximo;

    @ManyToMany
    @JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "grupo_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<Usuario> usuarios;

    @ManyToMany
    @JoinTable(name = "moderador_grupo", joinColumns = @JoinColumn(name = "grupo_id"), inverseJoinColumns = @JoinColumn(name = "moderador_id"))
    private List<Usuario> moderadores;

    @OneToOne
    @JoinColumn(name = "administrador_id")
    private Usuario administrador;

    private static final long serialVersionUID = 1L;

}
