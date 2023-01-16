package org.jlobato.imputaciones.service;

import java.util.Date;
import java.util.List;

import org.jlobato.imputaciones.model.Imputacion;
import org.jlobato.imputaciones.model.ImputacionIndividual;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.RedMine;

/**
 * The Interface SETAService.
 *
 * @author Jesús Manuel Pérez
 */
public interface ImputacionService {
	
	/**
	 * Inits the.
	 */
	public void init();
	
	/**
	 * Do imputaciones.
	 *
	 * @param redmine the redmine
	 * @param imputaciones the imputaciones
	 */
	public void doImputaciones(RedMine redmine, List<Imputacion> imputaciones);
	
	
	/**
	 * Do imputacion.
	 *
	 * @param redmine the redmine
	 * @param imputaciones the imputaciones
	 */
	public void doImputacion(RedMine redmine, Imputacion imputaciones);
	
	public void tiempoDedicado(RedMine redmine, Persona persona, Integer issueId, Date fechaImputacion, Float horas, Integer activityId, String comentario);
	
	public void tiempoDedicado(RedMine redmine, Persona persona, Integer issueId, String fechaImputacion, Float horas, Integer activityId, String comentario);
	
	public void tiempoDedicado(RedMine redmine, ImputacionIndividual imputacion);
	
	public void creaRelacion(RedMine redmine, Persona persona, Integer sourceId, Integer targetId);

}
