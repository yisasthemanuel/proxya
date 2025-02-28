package org.jlobato.imputaciones.model.manipuladores;

import org.jlobato.imputaciones.model.Peticion;

/**
 * The Interface ManipuladorPeticion.
 */
public interface ManipuladorPeticion {
	
	/**
	 * Manipula asunto.
	 *
	 * @param peticion the peticion
	 * @return the string
	 */
	public String manipulaAsunto(Peticion peticion);
}
