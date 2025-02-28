package org.jlobato.imputaciones.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The Interface Peticion.
 */
public interface Peticion extends Serializable {
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId();
	
	/**
	 * Gets the asunto.
	 *
	 * @return the asunto
	 */
	public String getAsunto();
	
	/**
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion();
	
	/**
	 * Gets the fecha inicio.
	 *
	 * @return the fecha inicio
	 */
	public Date getFechaInicio();
	
	/**
	 * Gets the fecha fin.
	 *
	 * @return the fecha fin
	 */
	public Date getFechaFin();
	
	/**
	 * Gets the tipo peticion.
	 *
	 * @return the tipo peticion
	 */
	public Integer getTipoPeticion();
	
	public Integer getCategoria();
	
	public Integer getIdAsignado();
	
	public Integer getIdPeticionPadre();
	
	public Integer getIdPrioridad();
}
