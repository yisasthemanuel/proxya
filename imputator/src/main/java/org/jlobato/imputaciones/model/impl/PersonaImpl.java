package org.jlobato.imputaciones.model.impl;

import org.jlobato.imputaciones.model.Persona;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Gets the api key.
 *
 * @return the api key
 */
@Getter

/**
 * Sets the api key.
 *
 * @param apiKey the new api key
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
public class PersonaImpl implements Persona {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2048731127969708309L;
	
	/** The id. */
	private String id;
	
	/** The nickname. */
	private String nickname;
	
	/** The nombre. */
	private String nombre;
	
	/** The primer apellido. */
	private String primerApellido;
	
	/** The segundo apellido. */
	private String segundoApellido;
	
	/** The nombre completo. */
	private String nombreCompleto;
	
	/** The api key. */
	private String apiKey;
	
}
