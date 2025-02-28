package org.jlobato.imputaciones.model.impl;

import org.jlobato.imputaciones.model.Actividad;

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
public class ActividadImpl implements Actividad {
	
	/** The id. */
	private int id;
	
	/** The descripcion. */
	private String descripcion;
}
