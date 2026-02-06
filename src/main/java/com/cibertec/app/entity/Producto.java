package com.cibertec.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")

public class Producto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idproducto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProd;
	
	@Column(name = "codigo")
	private String codigo; // Ej: "P001"
	
	@Column(name = "descripcion")
	private String descripcion; // Ej: "Laptop HP"
	
	@Column(name = "precio_compra")
	private BigDecimal precioCompra; // BigDecimal para manejar dinero con precisión
	
	@Column(name = "precio_venta")
	private BigDecimal precioVenta;
	
	@Column(name = "stock")
	private int stock; // Cantidad disponible
	
	// RELACIÓN: Un producto pertenece a UNA categoría
	@ManyToOne
	@JoinColumn(name = "idcate") // Columna que conecta con la tabla "categorias"
	private Categoria categoria;
	
	// Constructor personalizado (solo con código)
	public Producto(String codigo) {
		this.codigo = codigo;
	}
	
	// MÉTODO PERSONALIZADO: restar del stock cuando se vende
	public void restarExistencia(int stock) {
		this.stock -= stock; // Equivale a: this.stock = this.stock - cantidad
	}
	
	// MÉTODO PERSONALIZADO: verificar si no hay stock
	public boolean sinExistencia() {
		return this.stock <= 0; // Retorna true si stock es 0 o menor
	}

}
