package org.jlobato.imputaciones.service;

import org.jlobato.imputaciones.model.Proyecto;

public interface ProyectoService {
	public Proyecto creaProyecto(String identificador, String nombre, String descripcion);
	public Proyecto getProyectoByIdentificador(String identificador);
}
