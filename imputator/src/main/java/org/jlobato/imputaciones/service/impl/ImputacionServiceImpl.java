package org.jlobato.imputaciones.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jlobato.imputaciones.model.Imputacion;
import org.jlobato.imputaciones.model.ImputacionIndividual;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.RedMine;
import org.jlobato.imputaciones.service.ImputacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class SETAServiceImpl.
 */
@Service

/** The Constant log. */

/** The Constant log. */
@Slf4j
/**
 * 
 * @author Jesús Manuel Pérez
 *
 */
public class ImputacionServiceImpl implements ImputacionService {
	
	/** The Constant sdf. */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	/** The RedMine driver. */
	@Autowired
	RedMineDriver redmineDriver;

	/**
	 * Inits the SETAServiceImpl.
	 */
	@Override
	/**
	 * 
	 */
	public void init() {
		redmineDriver.init();
		log.info("ImputacionServiceImpl iniciado");
	}
	
	/**
	 * Do imputaciones.
	 *
	 * @param redmine the redmine
	 * @param imputaciones the imputaciones
	 */
	@Override
	/**
	 * 
	 */
	public void doImputaciones(RedMine redmine, List<Imputacion> imputaciones) {
		log.info("Proceso de imputaciones iniciado. Imputando {} peticiones de servicio", imputaciones.size());
		int i = 1;
		for (Imputacion imputacion : imputaciones) {
			doImputacion(redmine, imputacion);
			log.info("Petición procesada {}/{}", i++, imputaciones.size());
		}
		log.info("Proceso de imputaciones finalizado.");
		log.info("##");
	}

	/**
	 * Do imputacion.
	 *
	 * @param redmine the redmine
	 * @param imputacion the imputacion
	 */
	@Override
	public void doImputacion(RedMine redmine, Imputacion imputacion) {
		log.info("Imputando la petición de servicio con ID [{}]", imputacion.getId());
		redmineDriver.doImputacion(redmine, imputacion);
		log.info("Imputación de la petición de servicio con ID [{}] finalizada", imputacion.getId());
	}

	/**
	 * Tiempo dedicado.
	 *
	 * @param redmine the redmine
	 * @param persona the persona
	 * @param issueId the issue id
	 * @param fechaImputacion the fecha imputacion
	 * @param horas the horas
	 * @param activityId the activity id
	 * @param comentario the comentario
	 */
	@Override
	public void tiempoDedicado(RedMine redmine, Persona persona, Integer issueId, Date fechaImputacion, Float horas, Integer activityId, String comentario) {
		redmineDriver.tiempoDedicado(redmine.getUri(), persona.getApiKey(), issueId, fechaImputacion, horas, activityId, comentario);
	}

	/**
	 * Tiempo dedicado.
	 *
	 * @param redmine the redmine
	 * @param persona the persona
	 * @param issueId the issue id
	 * @param fechaImputacion the fecha imputacion
	 * @param horas the horas
	 * @param activityId the activity id
	 * @param comentario the comentario
	 */
	@Override
	public void tiempoDedicado(RedMine redmine, Persona persona, Integer issueId, String fechaImputacion, Float horas, Integer activityId, String comentario) {
		Date fecha;
		try {
			fecha = sdf.parse(fechaImputacion);
			this.tiempoDedicado(redmine, persona, issueId, fecha, horas, activityId, comentario);
		} catch (ParseException e) {
			log.error("Error parseando la fecha", e);
		}
	}

	/**
	 * Tiempo dedicado.
	 *
	 * @param redmine the redmine
	 * @param imputacion the imputacion
	 */
	@Override
	public void tiempoDedicado(RedMine redmine, ImputacionIndividual imputacion) {
		redmineDriver.tiempoDedicado(redmine.getUri(), imputacion.getPersona().getApiKey(), imputacion.getId(), imputacion.getFecha(), imputacion.getHoras(), imputacion.getActividad(), imputacion.getComentario());
	}

	@Override
	public void creaRelacion(RedMine redmine, Persona persona, Integer sourceId, Integer targetId) {
		redmineDriver.creaRelacion(redmine.getUri(), persona.getApiKey(), sourceId, targetId);
	}

}
