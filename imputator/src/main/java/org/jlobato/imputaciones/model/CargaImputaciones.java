package org.jlobato.imputaciones.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The Interface CargaImputaciones.
 */
public interface CargaImputaciones extends Serializable {
	
	/**
	 *  The id.
	 *
	 * @return the id
	 */
	public String getId();
	
	/**
	 *  The fecha ejecucion.
	 *
	 * @return the fecha ejecucion
	 */
	public Date getFechaEjecucion();
	
	/**
	 *  The imputaciones.
	 *
	 * @return the imputaciones
	 */
	public List<Imputacion> getImputaciones();
	
	/**
	 *  The destino.
	 *
	 * @return the destino
	 */
	public RedMine getDestino();
}
