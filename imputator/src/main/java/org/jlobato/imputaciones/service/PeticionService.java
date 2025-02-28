package org.jlobato.imputaciones.service;

import java.util.Date;
import java.util.List;

import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.Peticion;
import org.jlobato.imputaciones.model.Proyecto;
import org.jlobato.imputaciones.model.RedMine;

//TODO Poner en orden los métodos creaPeticion

/**
 * The Interface PeticionService.
 */
public interface PeticionService {
	
	/**
	 * Gets the peticion.
	 *
	 * @param redmine the redmine
	 * @param persona the persona
	 * @param idPeticion the id peticion
	 * @return the peticion
	 */
	public Peticion getPeticion(RedMine redmine, Persona persona, Integer idPeticion);
	
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
	
	/**
	 * Crea peticion.
	 *
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @return the peticion
	 */
	public Peticion creaPeticion(RedMine redmine, Proyecto proyecto);
	
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
	 * Crea peticion.
	 *
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @param autor the autor
	 * @param asunto the asunto
	 * @param descripcion the descripcion
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @return the peticion
	 */
	public Peticion creaPeticion(RedMine redmine, Proyecto proyecto, Persona autor, String asunto, String descripcion, Date fechaInicio, Date fechaFin);
	
	/**
	 * Crea peticion.
	 *
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @param autor the autor
	 * @param asunto the asunto
	 * @param descripcion the descripcion
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @param idTipoPeticion the id tipo peticion
	 * @param categoria 
	 * @return the peticion
	 */
	public Peticion creaPeticion(RedMine redmine, Proyecto proyecto, Persona autor, String asunto, String descripcion, Date fechaInicio, Date fechaFin, Integer idTipoPeticion);

	/**
	 * Crea peticion.
	 *
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @param autor the autor
	 * @param asunto the asunto
	 * @param descripcion the descripcion
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @param idTipoPeticion the id tipo peticion
	 * @param categoria 
	 * @return the peticion
	 */
	public Peticion creaPeticion(RedMine redmine, Proyecto proyecto, Persona autor, String asunto, String descripcion, Date fechaInicio, Date fechaFin, Integer idTipoPeticion, Integer categoria);
	
	public Peticion copiaPeticion(RedMine redmine, Proyecto proyecto, Persona autor, Peticion peticion);
	
}
