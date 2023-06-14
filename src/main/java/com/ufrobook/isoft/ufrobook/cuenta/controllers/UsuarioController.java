package com.ufrobook.isoft.ufrobook.cuenta.controllers;

import com.ufrobook.isoft.ufrobook.cuenta.models.entity.*;
import com.ufrobook.isoft.ufrobook.cuenta.services.impl.EmailService;
import com.ufrobook.isoft.ufrobook.cuenta.services.impl.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    //ESTA FUNCION SOLO EXISTE PARA CREAR USUARIOS EN EJECUCION
    public void crearUsuario(Usuario usuario){
        String claveEncriptada = passwordEncoder.encode(usuario.getClave());
        usuario.setClave(claveEncriptada);
        usuarioService.guardarUsuario(usuario);
    }

    //mapea el usuario con las credenciales almacenadas en el objeto authentication
    //obtiene las credenciales del token entregado y hace una busqueda en la base de datos
    //en base a esas credenciales, devolviendo el usuario en la base de datos
    @GetMapping("/sesion")
    public Usuario obtenerUsuarioSesion(Authentication authentication) {
        return usuarioService.obtenerUsuarioSesion(authentication);
    }
    @GetMapping("/amigos")
    public List<Usuario> getAmigos(Authentication authentication) {
        return usuarioService.obtenerUsuarioSesion(authentication).getAmigos();
    }


    //lista a todos los usuarios con el /usuarios
    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }

    //trae un usuario especifico con su id /usuarios/id
    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }


    //actualizar los datos del usuario con /usuarios/id y una solicitud put con los datos a cambiar
    @PutMapping("/actualizarUsuario")  // el usuario actualizado es solo para manejar los datos que nuestro modulo actualiza
    public ResponseEntity<String> actualizarUsuario(Authentication authentication, @RequestBody UsuarioActualizado usuarioActualizado) {
        try {
            Usuario usuarioExistente = usuarioService.obtenerUsuarioSesion(authentication);

            usuarioExistente.setEmail(usuarioActualizado.getEmail());
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setApellido(usuarioActualizado.getApellido());
            usuarioExistente.setNombreUsuario(usuarioActualizado.getNombreUsuario());

            usuarioService.guardarUsuario(usuarioExistente);

            return ResponseEntity.ok("Usuario actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el usuario: " + e.getMessage());
        }
    }


    // Endpoint para el registro de usuarios
    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setClave(usuarioDTO.getClave());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());

        // Generar token de verificación
        String token = UUID.randomUUID().toString();
        usuario.setTokenVerificacion(token);
        usuario.setFechaCreacionTokenVerificacion(LocalDateTime.now());

        // Encriptar la contraseña
        String claveEncriptada = passwordEncoder.encode(usuario.getClave());
        usuario.setClave(claveEncriptada);

        // Guardar el usuario en la base de datos
        usuarioService.guardarUsuario(usuario);

        // Enviar correo electrónico de verificación
        String cuerpoMensaje = "Hola " + usuario.getNombre() + ",\n\n"
                + "Gracias por registrarte en UfroBook. Para verificar tu cuenta, haz clic en el siguiente enlace:\n\n"
                + "http://localhost:3001/usuarios/verificar?token=" + token;
        emailService.enviarCorreo(usuario.getEmail(), "Verificación de cuenta", cuerpoMensaje);

        return ResponseEntity.ok("Usuario registrado correctamente. Por favor, verifica tu cuenta a través del correo electrónico.");
    }

    // Endpoint para la verificación del registro de la cuenta
    @GetMapping("/verificar")
    public ResponseEntity<String> verificarCuenta(@RequestParam("token") String token) {
        Usuario usuario = usuarioService.obtenerUsuarioPorTokenVerificacion(token)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario != null && !usuario.isVerificado()) {
            LocalDateTime fechaCreacionToken = usuario.getFechaCreacionTokenVerificacion();
            LocalDateTime fechaActual = LocalDateTime.now();

            // Calcular la diferencia de tiempo en minutos
            long minutosTranscurridos = ChronoUnit.MINUTES.between(fechaCreacionToken, fechaActual);
            final int DURACION_TOKEN_VERIFICACION_MINUTOS = 60;

            if (minutosTranscurridos <= DURACION_TOKEN_VERIFICACION_MINUTOS) {
                // Marcar la cuenta como verificada
                usuario.setVerificado(true);
                usuario.setTokenVerificacion(null); // Limpiar el token de verificación
                usuarioService.guardarUsuario(usuario);

                return ResponseEntity.ok("Cuenta verificada exitosamente");
            } else {
                return ResponseEntity.badRequest().body("El token de verificación ha expirado. Solicita un nuevo correo de verificación.");
            }
        } else {
            return ResponseEntity.badRequest().body("Error al verificar la cuenta");
        }
    }

    @PostMapping("/recuperar-contrasena")
    public ResponseEntity<String> recuperarContrasena(@RequestParam("email") String email) {
        // Verificar si existe un usuario con el correo electrónico proporcionado
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario != null) {
            // Generar un nuevo token de recuperación de contraseña y asignarlo al usuario
            String tokenRecuperacion = UUID.randomUUID().toString();
            usuario.setTokenVerificacion(tokenRecuperacion);
            usuarioService.guardarUsuario(usuario);

            String cuerpoMensaje = "Hola " + usuario.getNombre() + ",\n\n"
                    + "Has solicitado una recuperación de tu contraseña. Si has sido tú quien la ha solicitado, haz clic en el siguiente enlace:\n\n"
                    + "http://localhost:3001/usuarios/verificar-token-recuperacion?token=" + tokenRecuperacion;
            emailService.enviarCorreo(usuario.getEmail(), "Recuperar contraseña", cuerpoMensaje);

            return ResponseEntity.ok("Se ha enviado un correo electrónico con instrucciones para recuperar la contraseña.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún usuario con el correo electrónico proporcionado.");
        }
    }

    @GetMapping("/verificar-token-recuperacion")
    public Usuario verificarTokenRecuperacion(@RequestParam("token") String token) {
        // Verificar si existe un usuario con el token de verificación proporcionado
        Usuario usuario = usuarioService.obtenerUsuarioPorTokenVerificacion(token)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario != null) {
            LocalDateTime fechaCreacionToken = usuario.getFechaCreacionTokenVerificacion();
            LocalDateTime fechaActual = LocalDateTime.now();

            // Calcular la diferencia de tiempo en minutos
            long minutosTranscurridos = ChronoUnit.MINUTES.between(fechaCreacionToken, fechaActual);
            final int DURACION_TOKEN_VERIFICACION_MINUTOS = 60;

            if (minutosTranscurridos <= DURACION_TOKEN_VERIFICACION_MINUTOS) {
                usuario.setTokenVerificacion(null); // Limpiar el token de verificación
                usuarioService.guardarUsuario(usuario);
                //Lógica para cambiar contraseña
                ResponseEntity.ok("Token de recuperación de contraseña válido. Por favor, ingrese su nueva contraseña.");
                return usuario;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    @PostMapping("/ingreso-contrasena-recuperacion")
    public ResponseEntity<String> cambiarContrasena(@RequestBody RecuperarContrasenaRequest request) {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(request.getId()).
                    orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            // Actualizar la contraseña del usuario con la nueva contraseña ingresada
            usuario.setClave(passwordEncoder.encode(request.getNuevaContrasena()));
            usuarioService.guardarUsuario(usuario);

            return ResponseEntity.ok("Contraseña cambiada exitosamente. Por favor, inicie sesión con su nueva contraseña.");
    }

    @PostMapping("/solicitar-eliminacion")
    public ResponseEntity<String> solicitarEliminacionCuenta(Authentication authentication) {
        Usuario usuario = usuarioService.obtenerUsuarioSesion(authentication);
        if (usuario != null) {
            // Generar el token de verificación
            String token = UUID.randomUUID().toString();
            usuario.setTokenVerificacion(token);
            usuarioService.guardarUsuario(usuario);

            String cuerpoMensaje = "Hola " + usuario.getNombre() + ",\n\n"
                    + "Se ha solicitado la eliminación de la cuenta asociada a este correo. Si has sido tú quien la ha solicitado, haz clic en el siguiente enlace:\n\n"
                    + "http://localhost:3001/usuarios/eliminar-cuenta/" + token;
            emailService.enviarCorreo(usuario.getEmail(), "Eliminar cuenta", cuerpoMensaje);

            return ResponseEntity.ok("Solicitud de eliminación de cuenta exitosa. Por favor, revisa tu correo electrónico para completar el proceso.");
        } else {
            return ResponseEntity.badRequest().body("No se encontró un usuario registrado con el email proporcionado");
        }
    }

    @GetMapping("/eliminar-cuenta/{token}")
    public ResponseEntity<String> eliminarCuenta(@PathVariable("token") String token) {
        Usuario usuario = usuarioService.obtenerUsuarioPorTokenVerificacion(token).
                orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (usuario != null) {
            // Eliminar la cuenta del usuario
            usuarioService.eliminarUsuario(usuario.getId());

            return ResponseEntity.ok("La cuenta ha sido eliminada correctamente.");
        } else {
            return ResponseEntity.badRequest().body("El token de verificación es inválido o ha expirado.");
        }
    }

    @PutMapping("/cambiar-contrasena")
    public ResponseEntity<String> cambiarContrasena(Authentication authentication, @RequestBody CambioContrasenaRequest request) {

        Usuario usuario = usuarioService.obtenerUsuarioSesion(authentication);

        if (usuario != null) {
            // Verificar que la contraseña actual coincida
            if (passwordEncoder.matches(request.getContrasenaActual(), usuario.getClave())) {
                // Actualizar la contraseña con la nueva contraseña proporcionada
                usuario.setClave(passwordEncoder.encode(request.getNuevaContrasena()));
                usuarioService.guardarUsuario(usuario);

                return ResponseEntity.ok("La contraseña ha sido cambiada correctamente.");
            } else {
                return ResponseEntity.badRequest().body("La contraseña actual es incorrecta.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/cerrar-sesion")
    public ResponseEntity<String> cerrarSesion(Authentication authentication) {
        if (authentication != null) {
            // Obtener el usuario actual
            Usuario usuario = usuarioService.obtenerUsuarioSesion(authentication);

            // Eliminar el token del usuario
            usuario.setTokenSesion(null);
            usuarioService.guardarUsuario(usuario);

            // Invalidar la sesión actual y eliminar la autenticación
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("se ha cerrado la sesion");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se encontró una sesión de usuario");
        }
    }

}