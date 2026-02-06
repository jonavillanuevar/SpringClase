package com.cibertec.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cibertec.app.entity.Usuario;
import com.cibertec.app.service.RolService;
import com.cibertec.app.service.UsuarioService;

@Controller // Maneja peticiones HTTP
public class UsuarioController {
	
	// === INYECCIÓN DE DEPENDENCIAS ===
    @Autowired		// Spring inyecta automáticamente el servicio de usuarios
	private UsuarioService usuarioService;
	
	@Autowired		// Spring inyecta automáticamente el servicio de roles
	private RolService rolService;
	
	// ============================================================
    // MÉTODO 1: MOSTRAR PÁGINA DE LOGIN
    // ============================================================
	@GetMapping("/")  // Ruta: http://localhost:8080/
	public String login() {
		/*
         * Cuando el usuario visita la raíz del sitio (/)
         * - Se ejecuta este método
         * - Retorna "login" → Spring busca login.html en templates/
         * - Muestra el formulario de login
         */
		return "login"; // Retorna la vista login.html
	}
	
	
	// ============================================================
    // MÉTODO 2: MOSTRAR FORMULARIO DE REGISTRO
    // ============================================================
	@GetMapping("/register") // Ruta: http://localhost:8080/register
	public String showRegistrationForm(Model model) {
		
		/*
         * Cuando el usuario visita /register
         * - Crea un objeto Usuario vacío
         * - Lo agrega al Model para que esté disponible en la vista
         * - La vista (register.html) usa este objeto para el formulario
         */
		
		Usuario userDto = new Usuario(); // Usuario vacío
		
		// Model es como un "contenedor" que pasa datos a la vista
		model.addAttribute("usuario", userDto); // creamos el objeto de clase modelo
		return "register"; // para cargar la pagina de registro. Retorna la vista register.html
	}
	
	
	// ============================================================
    // MÉTODO 3: PROCESAR REGISTRO (GUARDAR USUARIO)
    // ============================================================
	@PostMapping("/register/save") // Ruta: POST http://localhost:8080/register/save
	public String registration(Usuario usuario,			// Datos del formulario 
							   BindingResult result,	// Maneja errores de validación
							   Model model) {
		/*
         * Cuando el usuario envía el formulario de registro:
         * 1. Valida que no exista el username
         * 2. Valida que todos los campos estén llenos
         * 3. Si hay errores → muestra el formulario de nuevo con mensajes
         * 4. Si todo OK → guarda el usuario y redirige con éxito
         */
		
		// === VALIDACIÓN 1: ¿El username ya existe? ===
		Usuario existingUser = usuarioService.buscarByUsuario(usuario.getUsername()); // Buscamos el usuario
		
		// === VALIDACIÓN 2: ¿Campos obligatorios llenos? ===
		if(usuario.getNombres() == null || usuario.getNombres().isEmpty()) // Validamos todos los datos
			result.rejectValue("nombres", null, "Ingresar nombres");
			// rejectValue agrega un error al campo "nombres"
		
		if(usuario.getApellidos() == null || usuario.getApellidos().isEmpty())
			result.rejectValue("apellidos", null, "Ingresar apellidos");
		
		if(usuario.getUsername() == null || usuario.getUsername().isEmpty())
			result.rejectValue("username", null, "Ingresar username");
		
		if(usuario.getClave() == null || usuario.getClave().isEmpty())
			result.rejectValue("clave", null, "Ingresar clave");
		
		// === VALIDACIÓN 3: ¿Username duplicado? ===
		if(existingUser != null)
			result.rejectValue("username", null, "Ya existe una cuenta con este usuario");
		
		// === SI HAY ERRORES: Volver al formulario ===
		if(result.hasErrors()) {
			model.addAttribute("usuario", usuario);	// Mantiene los datos ingresados
			return "/register"; // Si ocurre algún error de ingreso de datos, retornamos la página register. // Muestra register.html con los errores
		}
		
		// === SI TODO OK: Guardar usuario ===
		usuario.setRol(rolService.buscarById(2));	// Asigna rol por defecto (ID=2)
		usuarioService.guardarUsuario(usuario);		// Guarda en BD
		
		return "redirect:/register?success";		// Redirige a /register con parámetro ?success
		/*
         * redirect: hace que el navegador vaya a una nueva URL
         * ?success se puede usar en la vista para mostrar mensaje de éxito
         */
	}
	
	
	// ============================================================
    // MÉTODO 4: PROCESAR LOGIN (VALIDAR CREDENCIALES)
    // ============================================================
	@PostMapping("/login")	// Ruta: POST http://localhost:8080/login
	public String iniciarSesion(Model model, Usuario usuario) {
		/*
         * Cuando el usuario envía el formulario de login:
         * 1. Valida username y clave en BD
         * 2. Si coincide → muestra página principal con usuarios
         * 3. Si NO coincide → muestra página de error
         */
		
		// Valida credenciales
		boolean band = usuarioService.login(usuario);
		
		if(band == true) { // Login exitoso
			// Carga datos para mostrar en la página principal
			model.addAttribute("usuarios", usuarioService.listarTodosUsuario());
			model.addAttribute("rolList", rolService.listarTodosRol());
			return "usuario/index";	// Muestra usuario/index.html
		}else {	// Login fallido
			return "error_login";	// Muestra error_login.html
		}
	}
		
	
	// ============================================================
    // MÉTODO 5: MOSTRAR FORMULARIO PARA CREAR USUARIO (CRUD)
    // ============================================================
	@GetMapping("/usuario/new") // Ruta: GET http://localhost:8080/usuario/new
	public String createUsuarioForm(Model model) {
		/*
         * Muestra un formulario para crear un nuevo usuario
         * - Crea un Usuario vacío para el formulario
         * - Carga la lista de roles para un combo/select
         */
		
		// este objeto 'usuario' otorgará espacios de memoria a la página y listado de datos
		Usuario usuario = new Usuario();	// Objeto vacío
		model.addAttribute("usuario", usuario);	// Para el formulario
		model.addAttribute("rolList", rolService.listarTodosRol());	// Para el select de roles
		
		return "usuario/create";	// Muestra usuario/create.html
	}
	
	
	// ============================================================
    // MÉTODO 6: GUARDAR USUARIO (DESDE CRUD)
    // ============================================================
	@PostMapping("/usuario")	// Ruta: POST http://localhost:8080/usuario
	public String saveUsuario(Usuario usuario) {
		/*
         * Recibe los datos del formulario y guarda el usuario
         * Luego redirige a la lista de usuarios
         */
		
		usuarioService.guardarUsuario(usuario);	// Guarda en BD
		return "redirect:/usuario";				// Redirige a la lista (GET /usuario)
	}
	
	
	// ============================================================
    // MÉTODO 7: LISTAR USUARIOS (CRUD - INDEX)
    // ============================================================
	@GetMapping("/usuario")	// Ruta: GET http://localhost:8080/usuario
	public String listUsuario(Model model) {
		/*
         * Muestra la tabla de todos los usuarios
         * - Carga todos los usuarios de la BD
         * - Carga todos los roles (para filtros o mostrar)
         */
		
		model.addAttribute("usuarios", usuarioService.listarTodosUsuario());
		model.addAttribute("rolList", rolService.listarTodosRol());
		return "usuario/index";	// Muestra usuario/index.html con la tabla
	}
	
}
