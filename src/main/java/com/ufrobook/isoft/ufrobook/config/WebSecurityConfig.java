package com.ufrobook.isoft.ufrobook.config;

import com.ufrobook.isoft.ufrobook.cuenta.Security.JWTAuthenticationFilter;
import com.ufrobook.isoft.ufrobook.cuenta.Security.JWTAuthorizationFilter;
import com.ufrobook.isoft.ufrobook.cuenta.services.impl.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final UsuarioService usuarioService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(usuarioService);
        jwtAuthenticationFilter.setAuthenticationManager(authManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");


        return http.
                cors().
                and().
                csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("usuarios/registro", "usuarios/verificar"
                        //eliminar estos dos de abajo, dejar para que funcione la demostracion
                        //debido a que el navegador no esta guardando el token para esto
                        , "usuarios/eliminar-cuenta/**", "usuarios/verificar-token-recuperacion","usuarios/ingreso-contrasena-recuperacion",
                "usuarios/recuperar-contrasena")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }


    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
