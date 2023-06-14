package com.ufrobook.isoft.ufrobook;

import com.ufrobook.isoft.ufrobook.cuenta.controllers.UsuarioController;
import com.ufrobook.isoft.ufrobook.cuenta.models.entity.Usuario;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication()
public class UfrobookApplication implements ApplicationListener<ContextRefreshedEvent> {



	public static void main(String[] args) {
		SpringApplication.run(UfrobookApplication.class, args);
	}

	private final UsuarioController usuarioController;

	public UfrobookApplication(UsuarioController usuarioController) {
		this.usuarioController = usuarioController;

	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// Aquí puedes crear y guardar los usuarios de prueba
		crearUsuariosDePrueba();
	}

	private void crearUsuariosDePrueba() {
		Calendar calendar = Calendar.getInstance();

		// Usuario 1
		Usuario usuario1 = new Usuario();
		usuario1.setEmail("usuario1@example.com");
		usuario1.setNombreUsuario("usuario1");
		usuario1.setNombre("Nombre1");
		usuario1.setApellido("Apellido1");
		usuario1.setClave("password1");
		usuario1.setVerificado(true);


		calendar.set(1990, Calendar.JANUARY, 1); // Establece la fecha de nacimiento
		Date fechaNacimiento1 = calendar.getTime();
		usuario1.setFechaNacimiento(fechaNacimiento1);

		// Usuario 2
		Usuario usuario2 = new Usuario();
		usuario2.setEmail("usuario2@example.com");
		usuario2.setNombreUsuario("usuario2");
		usuario2.setNombre("Nombre2");
		usuario2.setApellido("Apellido2");
		usuario2.setClave("password2");

		usuario1.getAmigos().add(usuario2);
		usuario2.getAmigos().add(usuario1);

		calendar.set(1995, Calendar.APRIL, 15); // Establece la fecha de nacimiento
		Date fechaNacimiento2 = calendar.getTime();
		usuario2.setFechaNacimiento(fechaNacimiento2);

		// Encripta las contraseñas utilizando el método existente en UsuarioController
		usuarioController.crearUsuario(usuario1);
		usuarioController.crearUsuario(usuario2);

	}

}
