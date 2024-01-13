package org.jlobato.imputaciones.model.impl;

import org.jlobato.imputaciones.model.Proyecto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ProyectoImpl implements Proyecto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2250572497532253331L;
	private String identificador;
	private String nombre;
	private String descripcion;
}
