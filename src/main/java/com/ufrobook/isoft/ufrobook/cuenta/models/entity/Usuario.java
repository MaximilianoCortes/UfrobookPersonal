package com.ufrobook.isoft.ufrobook.cuenta.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table(name = "usuarios")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @NotBlank
    @Column(name = "nombre_pila")
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    //@Size(min = 8, max = 20)
    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    @JsonIgnore
    private String clave;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    private boolean verificado;

    @JsonIgnore
    private String tokenVerificacion;

    @JsonIgnore
    private LocalDateTime fechaCreacionTokenVerificacion;

    @JsonIgnore
    private String tokenSesion;


    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(name = "amigos", joinColumns = { @JoinColumn(name = "usuario_id") }, inverseJoinColumns = {
            @JoinColumn(name = "amigo_id") })
    private List<Usuario> amigos = new ArrayList<>();


    private static final long serialVersionUID = 1L;

}
