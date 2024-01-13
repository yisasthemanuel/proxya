package org.jlobato.imputaciones.model;

import java.io.Serializable;

public interface Proyecto extends Serializable {
	public String getIdentificador();
	public String getNombre();
	public String getDescripcion();
}
