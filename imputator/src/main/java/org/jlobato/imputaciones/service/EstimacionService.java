package org.jlobato.imputaciones.service;

import org.jlobato.imputaciones.model.Estimacion;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.RedMine;

/**
 * The Interface SETAService.
 *
 * @author Jesús Manuel Pérez
 */
public interface EstimacionService {
	
	/**
	 * Inits the.
	 */
	public void init();
	
	public void tiempoEstimado(RedMine redmine, Persona persona, Integer issueId, Float horas);
	
	public void tiempoEstimado(RedMine redmine, Estimacion estimacion);

}
