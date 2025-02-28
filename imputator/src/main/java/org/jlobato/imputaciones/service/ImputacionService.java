package org.jlobato.imputaciones.service;

import java.util.Date;
import java.util.List;

import org.jlobato.imputaciones.model.Imputacion;
import org.jlobato.imputaciones.model.ImputacionIndividual;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.Proyecto;
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
	
	/**
	 * Tiempo dedicado.
	 *
	 * @param redmine the redmine
	 * @param persona the persona
	 * @param issueId the issue id
	 * @param fechaImputacion the fecha imputacion
	 * @param horas the horas
	 * @param activityId the activity id
	 * @param comentario the comentario
	 */
	public void tiempoDedicado(RedMine redmine, Persona persona, Integer issueId, Date fechaImputacion, Float horas, Integer activityId, String comentario);
	
	/**
	 * Tiempo dedicado.
	 *
	 * @param redmine the redmine
	 * @param persona the persona
	 * @param issueId the issue id
	 * @param fechaImputacion the fecha imputacion
	 * @param horas the horas
	 * @param activityId the activity id
	 * @param comentario the comentario
	 */
	public void tiempoDedicado(RedMine redmine, Persona persona, Integer issueId, String fechaImputacion, Float horas, Integer activityId, String comentario);
	
	/**
	 * Tiempo dedicado.
	 *
	 * @param redmine the redmine
	 * @param imputacion the imputacion
	 */
	public void tiempoDedicado(RedMine redmine, ImputacionIndividual imputacion);
	
	/**
	 * Crea relacion.
	 *
	 * @param redmine the redmine
	 * @param persona the persona
	 * @param sourceId the source id
	 * @param targetId the target id
	 */
	public void creaRelacion(RedMine redmine, Persona persona, Integer sourceId, Integer targetId);
	
	/**
	 * Gets the imputaciones.
	 *
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @param persona the persona
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @return the imputaciones
	 */
	public List<ImputacionIndividual> getImputaciones(RedMine redmine, Proyecto proyecto, Persona persona, Date fechaInicio, Date fechaFin);
	
	/**
	 * Copia imputaciones.
	 *
	 * @param fechaInicioDate the fecha inicio date
	 * @param fechaFinDate the fecha fin date
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @param persona the persona
	 */
	public void copiaImputaciones(Date fechaInicioDate, Date fechaFinDate, RedMine redmine, Proyecto proyecto, Persona persona);

}
