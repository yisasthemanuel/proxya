package org.jlobato.imputaciones.model.impl;

import java.util.Date;

import org.jlobato.imputaciones.model.ImputacionIndividual;
import org.jlobato.imputaciones.model.Persona;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Gets the persona.
 *
 * @return the persona
 */
@Getter

/**
 * Sets the persona.
 *
 * @param persona the new persona
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
public class ImputacionIndividualImpl implements ImputacionIndividual {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4834090827271156618L;
	
	/** The id. */
	private int id;
	
	/** The fecha. */
	private Date fecha;
	
	/** The actividad. */
	private int actividad;
	
	/** The horas. */
	private float horas;
	
	/** The comentario. */
	private String comentario;
	
	/** The persona. */
	private Persona persona;
}
