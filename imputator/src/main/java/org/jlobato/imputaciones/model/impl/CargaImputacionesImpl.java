package org.jlobato.imputaciones.model.impl;

import java.util.Date;
import java.util.List;

import org.jlobato.imputaciones.model.CargaImputaciones;
import org.jlobato.imputaciones.model.Imputacion;
import org.jlobato.imputaciones.model.RedMine;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Gets the imputaciones.
 *
 * @return the imputaciones
 */

/**
 * Gets the destino.
 *
 * @return the destino
 */

/**
 * Gets the destino.
 *
 * @return the destino
 */

/**
 * Gets the destino.
 *
 * @return the destino
 */
@Getter

/**
 * Sets the imputaciones.
 *
 * @param imputaciones the new imputaciones
 */

/**
 * Sets the destino.
 *
 * @param destino the new destino
 */

/**
 * Sets the destino.
 *
 * @param destino the new destino
 */

/**
 * Sets the destino.
 *
 * @param destino the new destino
 */
@Setter

/**
 * To string.
 *
 * @return the java.lang. string
 */

/**
 * To string.
 *
 * @return the java.lang. string
 */

/**
 * To string.
 *
 * @return the java.lang. string
 */

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

/**
 * Hash code.
 *
 * @return the int
 */

/**
 * Hash code.
 *
 * @return the int
 */

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

/**
 * To string.
 *
 * @return the java.lang. string
 */

/**
 * To string.
 *
 * @return the java.lang. string
 */

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class CargaImputacionesImpl implements CargaImputaciones {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7998959317211167828L;
	
	/** The id. */
	private String id;
	
	/** The fecha ejecucion. */
	private Date fechaEjecucion;
	
	/** The imputaciones. */
	private List<Imputacion> imputaciones;
	
	/** The destino. */
	private RedMine destino;

}
