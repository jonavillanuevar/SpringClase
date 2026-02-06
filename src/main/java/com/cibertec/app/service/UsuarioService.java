package com.cibertec.app.service;

import java.util.List;

// Esta es una INTERFAZ (un contrato)
// Define QUÉ operaciones se pueden hacer, pero NO cómo se hacen (porque es una interfaz. Obvio)

import com.cibertec.app.entity.Usuario;

public interface UsuarioService {
	
	// Método 1: Guardar un usuario en la BD
    public Usuario guardarUsuario(Usuario registroDTO);
	
    // Método 2: Obtener todos los usuarios
	public List<Usuario> listarTodosUsuario();
	
	// Método 3: Verificar si un usuario puede iniciar sesión
	public boolean login(Usuario usuario);
	
	// Método 4: Buscar un usuario por su username
	public Usuario buscarByUsuario(String username);
}