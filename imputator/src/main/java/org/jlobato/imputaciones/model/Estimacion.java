package org.jlobato.imputaciones.model;

import java.io.Serializable;

/**
 * The Interface Estimacion.
 */
public interface Estimacion extends Serializable {
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId();
	
	/**
	 * Gets the persona.
	 *
	 * @return the persona
	 */
	public Persona getPersona();
	
	/**
	 * Gets the horas.
	 *
	 * @return the horas
	 */
	public float getHoras();
}
