package com.cibertec.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.app.entity.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, String>{
	
	// Una interfaz EXTIENDE otra interfaz
    // Hereda las DECLARACIONES de métodos
	
	// Contexto: Interfaz ← Interfaz
	// Qué heredas: Declaraciones de métodos (contratos)
	// Quién implementa: Spring Data JPA (automáticamente)
}
