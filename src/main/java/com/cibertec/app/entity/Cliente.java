package com.cibertec.app.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cliente")

public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idclie")
	private int idClie;
	
	@Column(name = "razon_soc")
	private String razonSoc; // Razón social (nombre de la empresa o persona)
	
	@Column(name = "nombre_ciudad")
	private String nombreCiudad;
	
	@Column(name = "direccion_clie")
	private String direccionClie;
	
	@Column(name = "telefono")
	private String telefono;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
