package org.jlobato.imputaciones.model.impl;

import java.util.Date;

import org.jlobato.imputaciones.model.Peticion;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Gets the fecha fin.
 *
 * @return the fecha fin
 */
@Getter

/**
 * Sets the fecha fin.
 *
 * @param fechaFin the new fecha fin
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
public class PeticionImpl implements Peticion {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2474440151867048821L;

	/** The id. */
	private int id;
	
	/** The asunto. */
	private String asunto;
	
	/** The descripcion. */
	private String descripcion;
	
	/** The fecha inicio. */
	private Date fechaInicio;
	
	/** The fecha fin. */
	private Date fechaFin;
	
	/** The tipo peticion. */
	private Integer tipoPeticion;
	
	/** The categoria. */
	private Integer categoria;
	
	private Integer idAsignado;
	
	private Integer idPeticionPadre;
	
	private Integer idPrioridad;
}
