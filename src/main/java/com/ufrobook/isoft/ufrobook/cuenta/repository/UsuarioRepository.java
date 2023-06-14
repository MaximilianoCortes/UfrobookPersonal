package com.ufrobook.isoft.ufrobook.cuenta.repository;

import com.ufrobook.isoft.ufrobook.cuenta.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //metodo para traer al usuario buscando por el nombre de usuario
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    Optional<Usuario> findByTokenVerificacion(String tokenVerificacion);

    Optional<Usuario> findOneByEmail(String email);
}
