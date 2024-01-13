package org.jlobato.imputaciones.service;

import java.util.List;

import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.Peticion;
import org.jlobato.imputaciones.model.Proyecto;
import org.jlobato.imputaciones.model.RedMine;

/**
 * The Interface PeticionService.
 */
public interface PeticionService {
	
	/**
	 * Crea peticion.
	 *
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @return the peticion
	 */
	public Peticion creaPeticion(RedMine redmine, Proyecto proyecto);
	
	/**
	 * Crea peticiones mensuales.
	 *
	 * @param anno the anno
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @return the list
	 */
	public List<Peticion> creaPeticionesMensuales(int anno, RedMine redmine, Proyecto proyecto);
	
	/**
	 * Crea peticiones mensuales.
	 *
	 * @param anno the anno
	 * @param mesInicio the mes inicio
	 * @param mesFin the mes fin
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @return the list
	 */
	public List<Peticion> creaPeticionesMensuales(int anno, int mesInicio, int mesFin, RedMine redmine, Proyecto proyecto);
	
	/**
	 * Crea peticion.
	 *
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @param autor the autor
	 * @return the peticion
	 */
	public Peticion creaPeticion(RedMine redmine, Proyecto proyecto, Persona autor);
	
	/**
	 * Crea peticiones mensuales.
	 *
	 * @param anno the anno
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @param autor the autor
	 * @return the list
	 */
	public List<Peticion> creaPeticionesMensuales(int anno, RedMine redmine, Proyecto proyecto, Persona autor);
	
	/**
	 * Crea peticiones mensuales.
	 *
	 * @param anno the anno
	 * @param mesInicio the mes inicio
	 * @param mesFin the mes fin
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @param autor the autor
	 * @return the list
	 */
	public List<Peticion> creaPeticionesMensuales(int anno, int mesInicio, int mesFin, RedMine redmine, Proyecto proyecto, Persona autor);
}
