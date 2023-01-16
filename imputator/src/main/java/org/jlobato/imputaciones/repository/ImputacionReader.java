package org.jlobato.imputaciones.repository;

import java.util.List;

import org.jlobato.imputaciones.model.Imputacion;


/**
 * The Interface ImputacionSETAReader.
 *
 * @author Jesús Manuel Pérez
 */
public interface ImputacionReader {

	/**
	 * Gets the imputaciones.
	 *
	 * @return the imputaciones
	 */
	public List<Imputacion> getImputaciones();
	
}
