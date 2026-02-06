package com.cibertec.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cibertec.app.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	//                                                      ↑       ↑
    //                                                   Entidad  Tipo del ID
	
	// Al extender JpaRepository, automáticamente obtienes estos métodos SIN escribir código:
    // - save(usuario)              → INSERT o UPDATE
    // - findById(id)               → SELECT * FROM usuario WHERE idusuario = ?
    // - findAll()                  → SELECT * FROM usuario
    // - deleteById(id)             → DELETE FROM usuario WHERE idusuario = ?
    // - count()                    → SELECT COUNT(*) FROM usuario
    // - existsById(id)             → Verifica si existe
	
	// MÉTODO PERSONALIZADO 1: Buscar por username
	@Query(value = "SELECT u.idusuario, u.usuario, u.nombres, u.apellidos, u.clave, u.idrol "
			+ "FROM usuario u WHERE u.usuario = :username", nativeQuery = true)
	//  											 ↑
	//                               Este es un parámetro que se pasa al método

	public Usuario findByUsuario(@Param("username") String username);
	//  							↑
	//          Le dice a Spring qué valor usar en la query
	
	//	 ¿Cómo se usa?
	//   Usuario juan = usuarioRepository.findByUsuario("jperez");
	//   SQL ejecutado: SELECT ... FROM usuario WHERE usuario = 'jperez'
	
	
	// MÉTODO PERSONALIZADO 2: Buscar por username Y clave (para login)
	@Query(value = "SELECT u.idusuario, u.usuario, u.nombres, u.apellidos, u.clave, u.idrol "
			+ "FROM usuario u WHERE u.usuario = :username and u.clave = :clave", nativeQuery = true)

	public Usuario findByUsuarioAndClave(@Param("username") String username, @Param("clave") String clave);
	
	// ¿Cómo se usa?
    // Usuario usuario = usuarioRepository.findByUsuarioAndClave("jperez", "123456");
    // Si encuentra el usuario con esas credenciales, lo retorna
    // Si no lo encuentra, retorna null
	
	//¿Qué es nativeQuery = true?
	//Significa que estás escribiendo SQL nativo (puro SQL de MySQL/PostgreSQL)
	//Si fuera false (o no lo pones), usarías JPQL (que usa nombres de clases Java en vez de tablas)

}