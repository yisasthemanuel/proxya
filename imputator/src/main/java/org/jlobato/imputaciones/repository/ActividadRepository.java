package org.jlobato.imputaciones.repository;

import java.util.List;

import org.jlobato.imputaciones.model.Actividad;

public interface ActividadRepository {
	public List<Actividad> getAllActividades();	
	public Actividad getActividad(String codigo);
	public void addActividad(Actividad actividad);
}
