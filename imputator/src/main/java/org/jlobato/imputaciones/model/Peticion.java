package org.jlobato.imputaciones.model;

import java.io.Serializable;
import java.util.Date;

public interface Peticion extends Serializable {
	public int getId();
	public String getAsunto();
	public String getDescripcion();
	public Date getFechaInicio();
	public Date getFechaFin();
}
