package org.jlobato.imputaciones.repository;

import java.util.List;

import org.jlobato.imputaciones.model.CargaImputaciones;


/**
 * The Interface SETACargaRepository.
 */
public interface CargaImputacionesRepository {
	
	/**
	 * Añade una carga de imputaciones en SETA. Se guarda una copia de las imputaciones en todas las peticiones involucradas
	 * en la carga.
	 *  
	 *
	 * @param carga the carga
	 */
	public void addCarga(CargaImputaciones carga);
	
	/**
	 * Recupera todas las imputaciones involucradas en una carga para su restauración en SETA.
	 *
	 * @param id the id
	 * @return the SETA carga
	 */
	public CargaImputaciones getCarga(String id);
	
	/**
	 * Devuelve la lista de cargas realizadas y que, por tanto, son susceptibles de ser restauradas.
	 *
	 * @return the cargas disponibles
	 */
	public List<CargaImputaciones> getCargasDisponibles();

}
