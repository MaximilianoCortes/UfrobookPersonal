package com.ufrobook.isoft.ufrobook.cuenta.models.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UsuarioDTO {
    private String email;
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String clave;
    private Date fechaNacimiento;
}
