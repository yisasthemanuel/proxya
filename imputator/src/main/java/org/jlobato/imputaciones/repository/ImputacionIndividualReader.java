package org.jlobato.imputaciones.repository;

import java.util.List;

import org.jlobato.imputaciones.model.ImputacionIndividual;

/**
 * The Interface ImputacionIndividualReader.
 */
public interface ImputacionIndividualReader {
	
	/**
	 * Gets the imputaciones.
	 *
	 * @return the imputaciones
	 */
	public List<ImputacionIndividual> getImputaciones();
}
