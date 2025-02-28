package org.jlobato.imputaciones.model;

import java.io.Serializable;

/**
 * The Interface Persona.
 */
public interface Persona extends Serializable, Comparable<Persona> {
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId();
	
	/**
	 * Gets the nickname.
	 *
	 * @return the nickname
	 */
	public String getNickname();
	
	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre();
	
	/**
	 * Gets the primer apellido.
	 *
	 * @return the primer apellido
	 */
	public String getPrimerApellido();
	
	/**
	 * Gets the segundo apellido.
	 *
	 * @return the segundo apellido
	 */
	public String getSegundoApellido();
	
	/**
	 * Gets the nombre completo.
	 *
	 * @return the nombre completo
	 */
	public String getNombreCompleto();
	
	/**
	 * Gets the api key.
	 *
	 * @return the api key
	 */
	public String getApiKey();

}
