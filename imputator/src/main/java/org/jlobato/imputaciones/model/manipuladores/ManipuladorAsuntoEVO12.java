package org.jlobato.imputaciones.model.manipuladores;

import org.jlobato.imputaciones.model.Peticion;

/**
 * The Class ManipuladorAsuntoEVO12.
 */
public class ManipuladorAsuntoEVO12 implements ManipuladorPeticion {

	/**
	 * Manipula asunto.
	 *
	 * @param peticion the peticion
	 * @return the string
	 */
	@Override
	public String manipulaAsunto(Peticion peticion) {
		return peticion.getAsunto() + " - EVO12";
	}

}
