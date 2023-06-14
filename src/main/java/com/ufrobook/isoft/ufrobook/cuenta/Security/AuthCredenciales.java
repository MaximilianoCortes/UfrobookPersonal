package com.ufrobook.isoft.ufrobook.cuenta.Security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthCredenciales {

    private String email;
    private String password;
}
