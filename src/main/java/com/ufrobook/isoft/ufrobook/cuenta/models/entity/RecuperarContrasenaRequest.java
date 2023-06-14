package com.ufrobook.isoft.ufrobook.cuenta.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecuperarContrasenaRequest {
    private String nuevaContrasena;
    private Long id;
}
