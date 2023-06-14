package com.ufrobook.isoft.ufrobook.mensajes.models.entity;

import java.util.List;

import com.ufrobook.isoft.ufrobook.cuenta.models.entity.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "chat_grupal")
public class ChatGrupal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "administrador_id")
    private Usuario administrador;

    @ManyToMany
    @JoinTable(name = "chat_grupal_usuario", joinColumns = @JoinColumn(name = "chat_grupal_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<Usuario> participantes;

    private static final long serialVersionUID = 1L;
}
