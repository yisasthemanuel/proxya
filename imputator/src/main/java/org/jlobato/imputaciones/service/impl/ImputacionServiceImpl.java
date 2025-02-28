package org.jlobato.imputaciones.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jlobato.imputaciones.model.Imputacion;
import org.jlobato.imputaciones.model.ImputacionIndividual;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.Peticion;
import org.jlobato.imputaciones.model.Proyecto;
import org.jlobato.imputaciones.model.RedMine;
import org.jlobato.imputaciones.service.EstimacionService;
import org.jlobato.imputaciones.service.ImputacionService;
import org.jlobato.imputaciones.service.PeticionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class SETAServiceImpl.
 */
@Service

/** The Constant log. */

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
	RedMineDriver redmineDriver;
	
	/** The peticion service. */
	PeticionService peticionService;
	
	/** The estimacion service. */
	EstimacionService estimacionService;

	/** The default persona. */
	Persona defaultPersona;
	
	/**
	 * Instantiates a new imputacion service impl.
	 *
	 * @param defaultPersona the default persona
	 */
	ImputacionServiceImpl(@Qualifier("getDefaultPersona") Persona defaultPersona) {
		this.defaultPersona = defaultPersona;
	}

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
	 * Sets the redmine driver.
	 *
	 * @param redmineDriver the new redmine driver
	 */
	@Autowired
	private void setRedmineDriver(RedMineDriver redmineDriver) {
		this.redmineDriver = redmineDriver;
	}
	
	/**
	 * Sets the peticion service.
	 *
	 * @param peticionService the new peticion service
	 */
	@Autowired
	private void setPeticionService(PeticionService peticionService) {
		this.peticionService = peticionService;
	}
	
	/**
	 * Sets the estimacion service.
	 *
	 * @param estimacionService the new estimacion service
	 */
	@Autowired
	private void setEstimacionService(EstimacionService estimacionService) {
		this.estimacionService = estimacionService;
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
		redmineDriver.tiempoDedicado(redmine, persona, issueId, fechaImputacion, horas, activityId, comentario);
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
		redmineDriver.tiempoDedicado(redmine, imputacion.getPersona(), imputacion.getId(), imputacion.getFecha(), imputacion.getHoras(), imputacion.getActividad(), imputacion.getComentario());
	}

	/**
	 * Crea relacion.
	 *
	 * @param redmine the redmine
	 * @param persona the persona
	 * @param sourceId the source id
	 * @param targetId the target id
	 */
	@Override
	public void creaRelacion(RedMine redmine, Persona persona, Integer sourceId, Integer targetId) {
		redmineDriver.creaRelacion(redmine, persona, sourceId, targetId);
	}

	/**
	 * Gets the imputaciones.
	 *
	 * @param redmine the redmine
	 * @param persona the persona
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @return the imputaciones
	 */
	@Override
	public List<ImputacionIndividual> getImputaciones(RedMine redmine, Proyecto proyecto, Persona persona, Date fechaInicio, Date fechaFin) {
		if (fechaInicio == null || fechaFin == null || redmine == null || proyecto == null || persona == null) {
			log.error("Alguno de los parámetros en getImputaciones es nulo: redmine={}, proyecto={}, persona={}, fechaInicio={}, fechaFin={}", redmine, proyecto, persona, fechaInicio, fechaFin);
			throw new IllegalStateException("Fecha de inicio no puede ser nula");
		}
		return redmineDriver.getImputaciones(redmine, proyecto, persona, fechaInicio, fechaFin);
	}

	/**
	 * Copia imputaciones.
	 *
	 * @param fechaInicioDate the fecha inicio date
	 * @param fechaFinDate the fecha fin date
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @param persona the persona
	 */
	@Override
	public void copiaImputaciones(Date fechaInicioDate, Date fechaFinDate, RedMine redmine, Proyecto proyecto, Persona persona) {
		List<ImputacionIndividual> imputaciones = this.getImputaciones(redmine, proyecto, persona, fechaInicioDate, fechaFinDate);
		log.debug("Imputaciones: {}", imputaciones);
		log.info("Se van a copiar {} imputaciones realizadas por {} en el proyecto {}", imputaciones.size(), persona.getNombreCompleto(), proyecto.getNombre());

		HashMap<Integer, Peticion> cachePeticionesImputacionesOrigen = new HashMap<>();
		HashMap<Integer, Peticion> cachePeticionesImputacionesDestino = new HashMap<>();
		HashMap<Integer, Float> cacheHorasImputadasPeticionDestino = new HashMap<>();

		// Imputaciones
		for (ImputacionIndividual imputacion : imputaciones) {
			// 1. Obtener la petición en la que se ha hecho la imputación y recuperar su
			// información de Redmine o de la caché
			Integer idPeticionImputacion = Integer.valueOf(imputacion.getId());
			Peticion peticionImputacionOrigen = cachePeticionesImputacionesOrigen.get(idPeticionImputacion);
			if (peticionImputacionOrigen == null) {
				log.debug("No se ha encontrado en la cache de peticiones de origen la información de la petición {}",
						idPeticionImputacion);
				peticionImputacionOrigen = peticionService.getPeticion(redmine, persona, idPeticionImputacion);
				// 2. Cacheamos la información de la petición donde imputó el pollo
				cachePeticionesImputacionesOrigen.put(idPeticionImputacion, peticionImputacionOrigen);
				log.debug("Cacheamos la información de la petición {}", idPeticionImputacion);
			}

			// 3. Mirar la caché de correlación entre peticion origen y destino.
			Peticion peticionImputacionDestino = cachePeticionesImputacionesDestino
					.get(peticionImputacionOrigen.getId());
			if (peticionImputacionDestino == null) {
				log.debug(
						"No se ha encontrado en la cache de peticiones destino de las imputaciones la correspondiente a {}",
						peticionImputacionOrigen.getId());
				// 4. Crear en el mismo proyecto una petición con el asunto modificado, misma
				// descripción y mismas fechas
				// TODO obtener la persona que creo la petición original para que sea él quien
				// figure en la creación - por ahora será la persona por defecto
				peticionImputacionDestino = peticionService.copiaPeticion(redmine, proyecto, defaultPersona,
						peticionImputacionOrigen);
				// 4. Cacheamos la información
				cachePeticionesImputacionesDestino.put(peticionImputacionOrigen.getId(),
						peticionImputacionDestino);
				log.debug("Cacheamos la petición {} que será la que reciba las peticiones de la {}",
						peticionImputacionDestino.getId(), peticionImputacionOrigen.getId());
			} else {
				log.debug("La petición {} es la destino de la imputación y ha sido cargada de la caché",
						peticionImputacionDestino.getId());
			}
			// 5. Hacemos la imputación
			this.tiempoDedicado(redmine, 
					imputacion.getPersona(),
					peticionImputacionDestino.getId(),
					imputacion.getFecha(),
					imputacion.getHoras(),
					imputacion.getActividad(),
					imputacion.getComentario());

			// 6. Guardamos las horas que se llevan imputadas para hacer luego la estimación
			Float horas = cacheHorasImputadasPeticionDestino.get(peticionImputacionDestino.getId());
			if (horas == null) {
				horas = Float.valueOf(0);
			}
			horas += imputacion.getHoras();
			cacheHorasImputadasPeticionDestino.put(peticionImputacionDestino.getId(), horas);
		}

		// Estimaciones
		log.info("{} procede a estimar las peticiones acorde las imputaciones realizadas en ellas", persona.getNombreCompleto());
		Iterator<Integer> peticionesDestinoIterator = cacheHorasImputadasPeticionDestino.keySet().iterator();
		while (peticionesDestinoIterator.hasNext()) {
			Integer idPeticionDestinoEstimacion = peticionesDestinoIterator.next();
			// Actualizamos el tiemo estimado en función de lo imputado
			estimacionService.tiempoEstimado(redmine, persona, idPeticionDestinoEstimacion, cacheHorasImputadasPeticionDestino.get(idPeticionDestinoEstimacion));
		}

		cacheHorasImputadasPeticionDestino.clear();
		cachePeticionesImputacionesDestino.clear();
		cachePeticionesImputacionesOrigen.clear();
		log.info("Imputaciones de {} realizadas con éxito", persona.getNombreCompleto());		
	}

}
