package com.ufrobook.isoft.ufrobook.cuenta.services.impl;

import com.ufrobook.isoft.ufrobook.cuenta.models.entity.Usuario;
import com.ufrobook.isoft.ufrobook.cuenta.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> obtenerUsuarioPorTokenVerificacion(String tokenVerificacion) {
        return usuarioRepository.findByTokenVerificacion(tokenVerificacion);
    }

    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findOneByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con email "+email+" no existe."));
        return new UserDetailsImpl(usuario);
    }

    //ESTA FUNCION ES PARA CARGAR AL USUARIO AUTENTICADO EN LA API MUY IMPORTANTE
    public Usuario obtenerUsuarioSesion(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) loadUserByUsername(authentication.getName());
        return obtenerUsuarioPorEmail(userDetails.getUsuario().getEmail()).
                orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

}