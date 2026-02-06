package com.cibertec.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.app.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {

	// No tiene métodos personalizados
    // Solo usa los métodos heredados de JpaRepository:
    // - findAll() → Listar todos los roles
    // - findById(id) → Buscar rol por ID
    // - save(rol) → Guardar rol
    // etc.
	
}
