package org.jlobato.imputaciones.service.impl;

import org.jlobato.imputaciones.model.Proyecto;
import org.jlobato.imputaciones.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class ProyectoServiceImpl.
 */
@Service

/** The Constant log. */
@Slf4j
public class ProyectoServiceImpl implements ProyectoService {
	
	/** The proyecto educamos. */
	@Autowired
	Proyecto proyectoEducamos;

	/**
	 * Crea proyecto.
	 *
	 * @param identificador the identificador
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @return the proyecto
	 */
	@Override
	public Proyecto creaProyecto(String identificador, String nombre, String descripcion) {
		return null;
	}

	/**
	 * Gets the proyecto by identificador.
	 *
	 * @param identificador the identificador
	 * @return the proyecto by identificador
	 */
	@Override
	public Proyecto getProyectoByIdentificador(String identificador) {
		//Por ahora siempre devolvemos el mismo: educamos
		log.info("Por ahora siempre devolvemos el mismo, educamos: {}", proyectoEducamos);
		return proyectoEducamos;
	}

}
