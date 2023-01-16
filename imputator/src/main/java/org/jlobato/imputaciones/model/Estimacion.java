package org.jlobato.imputaciones.model;

import java.io.Serializable;

public interface Estimacion extends Serializable {
	public int getId();
	public Persona getPersona();
	public float getHoras();
}
