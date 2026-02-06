package com.cibertec.app.service;

import java.util.List;

import com.cibertec.app.entity.Rol;

public interface RolService {
	
	// Listar todos los roles
	public List<Rol> listarTodosRol();
	
	// Buscar un rol por su ID
	public Rol buscarById(Integer id);
}