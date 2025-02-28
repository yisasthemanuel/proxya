package org.jlobato.imputaciones.model;

import java.io.Serializable;

/**
 * The Interface Proyecto.
 */
public interface Proyecto extends Serializable {
	
	/**
	 * Gets the identificador.
	 *
	 * @return the identificador
	 */
	public String getIdentificador();
	
	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre();
	
	/**
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion();
}
