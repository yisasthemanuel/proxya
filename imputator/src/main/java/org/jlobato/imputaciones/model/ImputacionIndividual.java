package org.jlobato.imputaciones.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The Interface ImputacionIndividual.
 */
public interface ImputacionIndividual extends Serializable {
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId();
	
	/**
	 * Gets the fecha.
	 *
	 * @return the fecha
	 */
	public Date getFecha();
	
	/**
	 * Gets the actividad.
	 *
	 * @return the actividad
	 */
	public int getActividad();
	
	/**
	 * Gets the horas.
	 *
	 * @return the horas
	 */
	public float getHoras();
	
	/**
	 * Gets the comentario.
	 *
	 * @return the comentario
	 */
	public String getComentario();
	
	/**
	 * Gets the persona.
	 *
	 * @return the persona
	 */
	public Persona getPersona();
	

}
