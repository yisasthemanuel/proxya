package org.jlobato.imputaciones.model.impl;

import org.jlobato.imputaciones.model.Proyecto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Gets the descripcion.
 *
 * @return the descripcion
 */
@Getter

/**
 * Sets the descripcion.
 *
 * @param descripcion the new descripcion
 */
@Setter

/**
 * To string.
 *
 * @return the java.lang. string
 */
@ToString

/**
 * Hash code.
 *
 * @return the int
 */
@EqualsAndHashCode

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class ProyectoImpl implements Proyecto {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2250572497532253331L;
	
	/** The identificador. */
	private String identificador;
	
	/** The nombre. */
	private String nombre;
	
	/** The descripcion. */
	private String descripcion;
}
