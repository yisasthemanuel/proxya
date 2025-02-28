package org.jlobato.imputaciones.service.impl;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.http.client.HttpClient;
import org.jlobato.imputaciones.config.RedMineProperties;
import org.jlobato.imputaciones.model.Estimacion;
import org.jlobato.imputaciones.model.Imputacion;
import org.jlobato.imputaciones.model.ImputacionIndividual;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.Peticion;
import org.jlobato.imputaciones.model.Proyecto;
import org.jlobato.imputaciones.model.RedMine;
import org.jlobato.imputaciones.model.impl.EstimacionImpl;
import org.jlobato.imputaciones.model.impl.ImputacionImpl;
import org.jlobato.imputaciones.model.impl.ImputacionIndividualImpl;
import org.jlobato.imputaciones.model.impl.PeticionImpl;
import org.jlobato.imputaciones.model.manipuladores.ManipuladorAsuntoEVO12;
import org.springframework.stereotype.Component;

import com.taskadapter.redmineapi.Include;
import com.taskadapter.redmineapi.IssueManager;
import com.taskadapter.redmineapi.NotFoundException;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.CustomField;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.IssueCategory;
import com.taskadapter.redmineapi.bean.IssueRelation;
import com.taskadapter.redmineapi.bean.TimeEntry;
import com.taskadapter.redmineapi.bean.Tracker;
import com.taskadapter.redmineapi.internal.ResultsWrapper;

import lombok.extern.slf4j.Slf4j;


/** The Constant log. */

/** The Constant log. */
@Slf4j
@Component
public class RedMineDriver {
	
	/** The Constant TEXTO_REQUISITO_SEGURIDAD. */
	private static final String TEXTO_REQUISITO_SEGURIDAD = "Para realizar este desarrollo se deben de tener en cuenta los siguientes requisitos de seguridad: https://seta.ceceu.junta-andalucia.es/projects/gestion-integral-de-ss-ii/wiki/REQUISITOS_SEGURIDAD_RECURRENTES_DESARROLLO";

	/** The Constant SEGURIDAD_CADENA_IDENTIFICATIVA. */
	private static final String SEGURIDAD_CADENA_IDENTIFICATIVA = "/REQUISITOS_SEGURIDAD_RECURRENTES_DESARROLLO";

	/** The Constant ERROR_EN_SETA. */
	private static final String ERROR_EN_SETA = "[{}] -> Petición con ID [{}] ha dado un error en SETA.";

	/** The http client. */
	HttpClient httpClient;

	/** The issue manager SETA. */
	IssueManager issueManagerDefault;

	/** The imputaciones fields. */
	Map<String, String> imputacionesFields;
	
	/** The seta properties. */
	RedMineProperties setaProperties;

	/** The formateador horas. */
	NumberFormat formateadorHoras;
	
	/** The formateador fecha. */
	SimpleDateFormat formateadorFecha;
	
	/** The formateador fecha restauracion. */
	SimpleDateFormat formateadorFechaRestauracion;
	
	/** The formateador fecha imputaciones. */
	SimpleDateFormat formateadorFechaImputaciones;
	
	/** The formateador textos. */
	MessageFormat formateadorTextos;
	
	/** The formateador params imputaciones. */
	SimpleDateFormat formateadorParamsImputaciones;
	
	/**
	 * Instantiates a new red mine driver.
	 *
	 * @param httpClient the http client
	 * @param imputacionesFields the imputaciones fields
	 * @param setaProperties the seta properties
	 * @param formateadorHoras the formateador horas
	 * @param formateadorFecha the formateador fecha
	 * @param formateadorFechaRestauracion the formateador fecha restauracion
	 * @param formateadorFechaImputaciones the formateador fecha imputaciones
	 * @param formateadorTextos the formateador textos
	 */
	public RedMineDriver(HttpClient httpClient, Map<String, String> imputacionesFields,
			RedMineProperties setaProperties, NumberFormat formateadorHoras, SimpleDateFormat formateadorFecha,
			SimpleDateFormat formateadorFechaRestauracion, SimpleDateFormat formateadorFechaImputaciones,
			MessageFormat formateadorTextos, SimpleDateFormat formateadorParamsImputaciones) {
		super();
		this.httpClient = httpClient;
		this.imputacionesFields = imputacionesFields;
		this.setaProperties = setaProperties;
		this.formateadorHoras = formateadorHoras;
		this.formateadorFecha = formateadorFecha;
		this.formateadorFechaRestauracion = formateadorFechaRestauracion;
		this.formateadorFechaImputaciones = formateadorFechaImputaciones;
		this.formateadorTextos = formateadorTextos;
		this.formateadorParamsImputaciones = formateadorParamsImputaciones;
	}


	/**
	 * Inits the.
	 */
	public void init() {
		RedmineManager mgrSETA = RedmineManagerFactory.createWithApiKey(setaProperties.getUri(), setaProperties.getApiAccessKey(), httpClient);
		issueManagerDefault = mgrSETA.getIssueManager();
		log.debug("RedMineDriver iniciado");
	}
	
	/**
	 * Do imputacion.
	 *
	 * @param issueManager the issue manager
	 * @param imputacion the imputacion
	 */
	public void doImputacion(IssueManager issueManager, Imputacion imputacion) {
		//Sólo procesamos imputaciones con ID mayor que cero.
		if (imputacion.getId() > 0) {
			try {
				// Petición a actualizar
				Issue issue = issueManager.getIssueById(imputacion.getId());
				
				double horasActualizadas = 0;
				
				// Esto hay que hacer para cada uno de los campos definidos
				for (Entry<String, String> fieldKey : imputacionesFields.entrySet()) {
					// Obtenemos el número de horas que tiene el campo en la petición actual (JA Permanente)
					String currentFieldValue = getCurrentValue(issue, fieldKey.getKey());
	
					// Obtenemos el número de horas que tiene la imputación (JA Permanente)
					String imputacionFieldValue = getImputacionValue(imputacion, fieldKey.getValue());
	
					// Sumamos las horas
					double horasPerfilImputacion = getDouble(imputacionFieldValue);
					horasActualizadas = getDouble(currentFieldValue) + horasPerfilImputacion;
					
					// Actualizamos la petición en RM con el nuevo valor, siempre y cuando sea mayor que cero
					if ((horasActualizadas > 0) || (horasPerfilImputacion > 0)) {
						setNewValue(issue, fieldKey.getKey(), horasActualizadas);
						log.debug("[{}]-[{}]={}", imputacion.getId(), fieldKey.getKey(), horasActualizadas);
					}
				}
	
				// Fecha de la imputación
				Date date = getImputacionDate(imputacion);
	
				// Texto para la nota de imputación
				String notesTemplate = "Horas dedicadas a esta tarea por parte del equipo del {0} durante el mes de {1}";
				@SuppressWarnings("static-access")
				String notes = formateadorTextos.format(notesTemplate, "lote 3", formateadorFecha.format(date));
	
				// Actualizamos la petición en el AMS destino
				issue.setNotes(notes);
				
				// Actualizamos descripción para incluir lo de seguridad en la petición - INI
				String descripcion = issue.getDescription();
				// Nos aseguramos que tenga descripcion
				if (descripcion == null) {
					descripcion = "";
				}
				if (descripcion.indexOf(SEGURIDAD_CADENA_IDENTIFICATIVA) == -1) {
					descripcion += System.lineSeparator()
							+ System.lineSeparator()
							+ TEXTO_REQUISITO_SEGURIDAD;
				}
				issue.setDescription(descripcion);
				// Actualizamos descripción para incluir lo de seguridad en la petición - FIN
				
				issue.update();
				
				log.info("Imputación en petición [{}]-[{}] realizada: {}", imputacion.getId(), issue.getSubject(), imputacion);
				
			} catch (NotFoundException nfe) {
				log.debug("Petición con ID {} no se encuentra en el RedMine destino. Ignoramos", imputacion.getId());
			} catch (RedmineException re) {
				log.error(ERROR_EN_SETA , "DO IMPUTACION", imputacion.getId(), re);
			}
		}
	}
	
	/**
	 * Do imputacion.
	 *
	 * @param redmine the redmine
	 * @param imputacion the imputacion
	 */
	public void doImputacion(RedMine redmine, Imputacion imputacion) {
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(redmine.getUri(), redmine.getApiKey(), httpClient);
		IssueManager issueManager = redMineManager.getIssueManager();
		doImputacion(issueManager, imputacion);
	}
	
	/**
	 * Tiempo dedicado.
	 *
	 * @param redmineURI the redmine URI
	 * @param apiKey the api key
	 * @param issueId the issue id
	 * @param fechaImputacion the fecha imputacion
	 * @param horas the horas
	 * @param activityId the activity id
	 * @param comentario the comentario
	 */
	public void tiempoDedicado(RedMine redmine, Persona persona, Integer issueId, Date fechaImputacion, Float horas, Integer activityId, String comentario) {
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(redmine.getUri(), persona.getApiKey(), httpClient);
		
		TimeEntry entry = new TimeEntry(redMineManager.getTransport());
		entry.setSpentOn(fechaImputacion);
		entry.setIssueId(issueId);
		entry.setHours(horas);
		entry.setActivityId(activityId);
		entry.setComment(comentario);
		try {
			entry.create();
		} catch (RedmineException e) {
			log.error("Excepción en la imputación {}", entry, e);
		}
		log.info("{} ha imputado {} horas el día {} en la petición {}", persona.getNombreCompleto(), horas, formateadorFechaImputaciones.format(fechaImputacion), issueId);

	}
	
	/**
	 * Crea relacion.
	 *
	 * @param redmineURI the redmine URI
	 * @param apiKey the api key
	 * @param sourceId the source id
	 * @param targetId the target id
	 */
	public void creaRelacion(RedMine redmine, Persona persona, Integer sourceId, Integer targetId) {
		String relationText = "relates";
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(redmine.getUri(), persona.getApiKey(), httpClient);
		try {
			new IssueRelation(redMineManager.getTransport(), sourceId, targetId, relationText).create();
		} catch (RedmineException e) {
			log.error("Excepción en al crear la relación entre la petición origen {} y la petición destino {}", sourceId, targetId, e);
		}
		
		log.info("Petición {} relacionada por el tipo {} con {} ", sourceId, relationText, targetId);
	}
	
	/**
	 * Do imputacion.
	 *
	 * @param imputacion the imputacion
	 * @deprecated
	 */
	@Deprecated(since = "0.0.2", forRemoval = false)
	public void doImputacion(Imputacion imputacion) {
		doImputacion(issueManagerDefault, imputacion);
	}
	
	/**
	 * Gets the imputacion previa.
	 *
	 * @param redmine the redmine
	 * @param imputacion the imputacion
	 * @return the imputacion previa
	 */
	public Imputacion getImputacionPrevia(RedMine redmine, Imputacion imputacion) {
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(redmine.getUri(), redmine.getApiKey(), httpClient);
		IssueManager issueManager = redMineManager.getIssueManager();
		return getImputacionPrevia(issueManager, imputacion);
	}
	
	/**
	 * Gets the imputacion previa.
	 *
	 * @param issueManager the issue manager
	 * @param imputacion the imputacion
	 * @return the imputacion previa
	 */
	public Imputacion getImputacionPrevia(IssueManager issueManager, Imputacion imputacion) {

		//Creamos la imputación con el mismo id, año y mes
		Imputacion imputacionBackup = ImputacionImpl.builder()
				.id(imputacion.getId())
				.anho(imputacion.getAnho())
				.mes(imputacion.getMes())
				.build();

		if (imputacion.getId() > 0) {

			try {
				// Petición a actualizar
				Issue issue = issueManager.getIssueById(imputacion.getId());

				// Esto hay que hacer para cada uno de los campos definidos
				for (Entry<String, String> fieldKey : imputacionesFields.entrySet()) {
					// Obtenemos el número de horas que tiene el campo en la petición actual
					String currentFieldValue = getCurrentValue(issue, fieldKey.getKey());

					// Actualizamos el número de horas que tiene la imputación
					setImputacionValue(imputacionBackup, fieldKey.getValue(), currentFieldValue);
				}
			} catch (NotFoundException nfe) {
				log.error("BACKUP -> Petición con ID [{}] no se encuentra en el RedMine destino.", imputacion.getId());
			} catch (RedmineException re) {
				log.error(ERROR_EN_SETA , "BACKUP", imputacion.getId(), re);
			}
		}

		return imputacionBackup;
	}
	
	
	/**
	 * Gets the imputacion previa.
	 *
	 * @param imputacion the imputacion
	 * @return the imputacion previa
	 * @deprecated
	 */
	@Deprecated(since = "0.0.2", forRemoval = false)
	public Imputacion getImputacionPrevia(Imputacion imputacion) {
		return getImputacionPrevia(issueManagerDefault, imputacion);
	}
	
	/**
	 * Sets the imputacion.
	 *
	 * @param issueManager the issue manager
	 * @param imputacion the imputacion
	 * @param fechaEjecucion the fecha ejecucion
	 */
	public void setImputacion(IssueManager issueManager, Imputacion imputacion, Date fechaEjecucion) {
		if (imputacion.getId() > 0) {
			try {
				Issue issue = issueManager.getIssueById(imputacion.getId());
			
				for (Entry<String, String> fieldKey : imputacionesFields.entrySet()) {
					String property = fieldKey.getValue();
					String propertyValue = getImputacionValue(imputacion, property);
					
					setNewValue(issue, fieldKey.getKey(), propertyValue);
				}
				
				// Texto para la nota de restauración
				String notesTemplate = "Restaurando las horas dedicadas a esta tarea por parte del equipo del {0} con loa valores que había antes de la carga realizada el {1}";
				@SuppressWarnings("static-access")
				String notes = formateadorTextos.format(notesTemplate, "lote 3", formateadorFechaRestauracion.format(fechaEjecucion));
				
				issue.setNotes(notes);
				issue.update();
				
				log.info("Imputación en petición [{}]-[{}] restaurada: {}", imputacion.getId(), issue.getSubject(), imputacion);
				
			} catch (NotFoundException nfe) {
				log.error("RESTORE -> Petición con ID [{}] no se encuentra en el RedMine destino.", imputacion.getId(), nfe);
			} catch (RedmineException re) {
				log.error(ERROR_EN_SETA , "RESTORE", imputacion.getId(), re);
			}
		}

	}

	
	/**
	 * Sets the imputacion.
	 *
	 * @param redmine the redmine
	 * @param imputacion the imputacion
	 * @param fechaEjecucion the fecha ejecucion
	 */
	public void setImputacion(RedMine redmine, Imputacion imputacion, Date fechaEjecucion) {
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(redmine.getUri(), redmine.getApiKey(), httpClient);
		IssueManager issueManager = redMineManager.getIssueManager();
		setImputacion(issueManager, imputacion, fechaEjecucion);
	}
	
	/**
	 * Sets the imputacion.
	 *
	 * @param imputacion the imputacion
	 * @param fechaEjecucion the fecha ejecucion
	 * @deprecated
	 */
	@Deprecated(since = "0.0.2", forRemoval = false)
	public void setImputacion(Imputacion imputacion, Date fechaEjecucion) {
		setImputacion(issueManagerDefault, imputacion, fechaEjecucion);	
	}

	/**
	 * Gets the double.
	 *
	 * @param value the value
	 * @return the double
	 */
	private double getDouble(String value) {
		// Lo pasamos a double
		Number number = null;
		if (value != null && !value.equals("")) {
			try {
				number = formateadorHoras.parse(value);
			} catch (ParseException e) {
				log.error("Error al obtener la imputación a partir del valor {}", value, e);
			}
		}

		return (number != null) ? number.doubleValue() : 0;
	}
	
	/**
	 * Gets the imputacion date.
	 *
	 * @param imputacion the imputacion
	 * @return the imputacion date
	 */
	private Date getImputacionDate(Imputacion imputacion) {
		// Fecha de la imputación
		Calendar calendar = Calendar.getInstance();
		calendar.set(imputacion.getAnho(), imputacion.getMes() - 1, 1);
		return calendar.getTime();
	}
	
	/**
	 * Gets the current value.
	 *
	 * @param issue the issue
	 * @param customFiledName the custom filed name
	 * @return the current value
	 */
	private String getCurrentValue(Issue issue, String customFiledName) {
		// Obtenemos el número de horas que tiene el campo en la petición actual (JA Permanente)
		CustomField custom = issue.getCustomFieldByName(customFiledName);
		return (custom != null) ? custom.getValue() : "0";
	}
	
	/**
	 * Sets the new value.
	 *
	 * @param issue the issue
	 * @param customFiledName the custom filed name
	 * @param newValue the new value
	 */
	private void setNewValue(Issue issue, String customFiledName, double newValue) {
		// Actualizamos la petición en RM con el nuevo valor
		String sHorasActualizadas = formateadorHoras.format(newValue);
		CustomField custom = issue.getCustomFieldByName(customFiledName);
		custom.setValue(sHorasActualizadas);
	}
	
	/**
	 * Gets the imputacion value.
	 *
	 * @param imputacion the imputacion
	 * @param property the property
	 * @return the imputacion value
	 */
	private String getImputacionValue(Imputacion imputacion, String property) {
		String result = "0";
		try {
			Object value = PropertyUtils.getProperty(imputacion, property);
			if (value != null) result = value.toString();
		} catch (Exception e) {
			log.error("Error obteniendo Propiedad [" + property + "] del objeto " + imputacion + ". Causa raíz: " + e.getClass().getName() + "->" + e.getMessage(), e);
		}
		
		return result;
	}
	
	/**
	 * Sets the imputacion value.
	 *
	 * @param imputacion the imputacion
	 * @param property the property
	 * @param value the value
	 */
	private void setImputacionValue(Imputacion imputacion, String property, Object value) {

		try {
			PropertyUtils.setProperty(imputacion, property, value);
		} catch (Exception e) {
			log.error("Error obteniendo Propiedad [" + property + "] del objeto " + imputacion + ". Causa raíz: " + e.getClass().getName() + "->" + e.getMessage(), e);
		}
		
	}

	/**
	 * Método duplicado que hay que llevar a la API.
	 *
	 * @param issue the issue
	 * @param customFiledName the custom filed name
	 * @param newValue the new value
	 */
	private void setNewValue(Issue issue, String customFiledName, String newValue) {
		// Actualizamos la petición en RM con el nuevo valor
		CustomField custom = issue.getCustomFieldByName(customFiledName);
		custom.setValue(newValue);
	}

	/**
	 * Tiempo estimado.
	 *
	 * @param redmine the redmine
	 * @param persona the persona
	 * @param issueId the issue id
	 * @param horas the horas
	 */
	public void tiempoEstimado(RedMine redmine, Persona persona, Integer issueId, Float horas) {
		this.tiempoEstimado(redmine, EstimacionImpl.builder().id(issueId).persona(persona).horas(horas).build());
	}

	/**
	 * Tiempo estimado.
	 *
	 * @param redmine the redmine
	 * @param estimacion the estimacion
	 */
	public void tiempoEstimado(RedMine redmine, Estimacion estimacion) {
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(redmine.getUri(), estimacion.getPersona().getApiKey(), httpClient);
		IssueManager issueManager = redMineManager.getIssueManager();
		try {
			Issue hu = issueManager.getIssueById(estimacion.getId());
			hu.setEstimatedHours(estimacion.getHoras());
			hu.update();
		} catch (RedmineException e) {
			log.error("Error al obtener la petición {}",  estimacion.getId(), e);
		}
		log.info("{} ha registrado una estimación de {} horas en la petición {}", estimacion.getPersona().getNombreCompleto(), estimacion.getHoras(), estimacion.getId());
	}

	/**
	 * Crea peticion.
	 *
	 * @param uri the uri
	 * @param idProyecto the id proyecto
	 * @param apiKey the api key
	 * @param asunto the asunto
	 * @param descripcion the descripcion
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @param idTipoPeticion 
	 * @param idCategoria 
	 * @return the peticion
	 * @deprecated
	 */
	@Deprecated
	public Peticion creaPeticion(String uri, String idProyecto, String apiKey, String asunto, String descripcion, Date fechaInicio, Date fechaFin, Integer idTipoPeticion, Integer idCategoria) {
		Peticion result = null;
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(uri, apiKey, httpClient);
        Issue issue = new Issue();
        issue.setTransport(redMineManager.getTransport());
        
        //Mejor meter una utilidad que saque el id del proyecto a partir del identificador 
        try {
			issue.setProjectId(redMineManager.getProjectManager().getProjectByKey(idProyecto).getId());
		} catch (RedmineException e) {
			log.error("Error al obtener el id de proyecto {}",  idProyecto, e);
		}
        issue.setSubject(asunto);
        issue.setDescription(descripcion);

        // Establecer la fecha de inicio y fecha de fin (si es aplicable)
        issue.setStartDate(fechaInicio);  // Reemplaza la fecha de inicio según tus necesidades
        issue.setDueDate(fechaFin);    // Reemplaza la fecha de fin según tus necesidades
        
        // Si llega el tipo de tarea, la asignamos a la petición
        if (idTipoPeticion != null) {
        	Iterator<Tracker> trackers;
			try {
				trackers = redMineManager.getIssueManager().getTrackers().iterator();
				boolean encontrado = false;
	        	while(trackers.hasNext() && !encontrado) {
	        		Tracker tracker = trackers.next();
	        		if (tracker.getId().equals(idTipoPeticion)) {
	        			issue.setTracker(tracker);
	        			encontrado = true;
	        		}
	        	}
			} catch (RedmineException e) {
				log.error("Error al obtener los trackers de Redmine", e);
			}
        }
        
        Issue createdIssue;
		try {
			createdIssue = issue.create();
			result = PeticionImpl.builder()
					.asunto(asunto)
					.descripcion(descripcion)
					.fechaInicio(fechaInicio)
					.fechaFin(fechaFin)
					.tipoPeticion(idTipoPeticion)
					.id(createdIssue.getId()).build();
		} catch (RedmineException e) {
			log.error("Error al crear la petición en el RedMine destino: {}",  uri, e);
		}
		
		return result;
	}

	/**
	 * Gets the imputaciones.
	 *
	 * @param uri the uri
	 * @param apiKey the api key
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @return the imputaciones
	 */
	public List<ImputacionIndividual> getImputaciones(RedMine redmine, Proyecto proyecto, Persona persona, Date fechaInicio, Date fechaFin) {
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(redmine.getUri(), persona.getApiKey(), httpClient);
		List<ImputacionIndividual> result = new ArrayList<>();
		try {
			int offset = 0;
			int limit = 100;

			ResultsWrapper<TimeEntry> timeEntriesWrapper;
			do {
				final Map<String, String> params = new HashMap<>();
				params.put("from", formateadorParamsImputaciones.format(fechaInicio));
				params.put("to", formateadorParamsImputaciones.format(fechaFin));
				params.put("offset", Integer.toString(offset));
				params.put("limit", Integer.toString(limit));
				if (proyecto.getIdentificador() != null) params.put("project_id", proyecto.getIdentificador());
				params.put("user_id", Integer.toString(redMineManager.getUserManager().getCurrentUser().getId()));

				timeEntriesWrapper = redMineManager.getTimeEntryManager().getTimeEntries(params);
				
				log.debug("getTotalFoundOnServer() -> {}", timeEntriesWrapper.getTotalFoundOnServer());
				log.debug("getLimitOnServer() -> {}", timeEntriesWrapper.getLimitOnServer());
				log.debug("getOffsetOnServer() -> {}", timeEntriesWrapper.getOffsetOnServer());
				log.debug("getResultsNumber() -> {}", timeEntriesWrapper.getResultsNumber());
				
				for (TimeEntry timeEntry : timeEntriesWrapper.getResults()) {
					result.add(ImputacionIndividualImpl.builder()
						.actividad(timeEntry.getActivityId())
						.comentario(timeEntry.getComment())
						.fecha(timeEntry.getSpentOn())
						.id(timeEntry.getIssueId())
						.horas(timeEntry.getHours())
						.persona(persona)
						.build());
					log.debug("Time spent: {} by {} on {}"
							, timeEntry.getHours()
							, timeEntry.getUserName()
							, timeEntry.getSpentOn());
				}
				offset += limit;
			} while (!timeEntriesWrapper.getResults().isEmpty());
		} catch (RedmineException e) {
			log.error("RedmineException al obtener las imputaciones de " + persona.getNombreCompleto() + " en el proyecto " + proyecto.getNombre() + " del redmine " + redmine.getDescription(), e);
			result.clear();
		}
		
		return result;
	}


	/**
	 * Gets the peticion.
	 *
	 * @param redMine the red mine
	 * @param persona the persona
	 * @param idPeticion the id peticion
	 * @return the peticion
	 */
	public Peticion getPeticion(RedMine redmine, Persona persona, Integer idPeticion) {
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(redmine.getUri(), persona.getApiKey(), httpClient);
		Peticion result = null;
		try {
			Issue issue = redMineManager.getIssueManager().getIssueById(idPeticion, new Include[] {});
			//at this point, issue never should be null
			result = PeticionImpl.builder()
					.asunto(issue.getSubject())
					.descripcion(issue.getDescription())
					.fechaInicio(issue.getStartDate())
					.fechaFin(issue.getDueDate())
					.tipoPeticion(issue.getTracker().getId())
					.categoria((issue.getCategory() == null) ? null : issue.getCategory().getId())
					.idAsignado(issue.getAssigneeId())
					.idPeticionPadre(issue.getParentId())
					.idPrioridad(issue.getPriorityId())
					.id(idPeticion).build();
			//TODO Copiar asignado y categoría, así como peticion padre y prioridad
		} catch (RedmineException e) {
			log.error("RedmineException " + redmine.getUri() + " " + " apiKey: " + persona.getApiKey(), e);
		}
		
		return result;
	}


	/**
	 * Copia peticion.
	 *
	 * @param uri the uri
	 * @param idProyecto the id proyecto
	 * @param apiKey the api key
	 * @param peticion the peticion
	 * @return the peticion
	 */
	public Peticion copiaPeticion(String uri, String idProyecto, String apiKey, Peticion peticion) {
		Peticion result = null;
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(uri, apiKey, httpClient);
        Issue issue = new Issue();
        issue.setTransport(redMineManager.getTransport());
        
        //Mejor meter una utilidad que saque el id del proyecto a partir del identificador 
        try {
			issue.setProjectId(redMineManager.getProjectManager().getProjectByKey(idProyecto).getId());
		} catch (RedmineException e) {
			log.error("Error al obtener el id de proyecto {}",  idProyecto, e);
		}
        //TODO Manipular del asunto para hacer las copias con un bean de spring
        issue.setSubject(new ManipuladorAsuntoEVO12().manipulaAsunto(peticion));
        issue.setDescription(peticion.getDescripcion());

        // Establecer la fecha de inicio y fecha de fin (si es aplicable)
        issue.setStartDate(peticion.getFechaInicio());  // Reemplaza la fecha de inicio según tus necesidades
        issue.setDueDate(peticion.getFechaFin());    // Reemplaza la fecha de fin según tus necesidades
        
        // Si llega el tipo de tarea, la asignamos a la petición
        setTipoPeticion(peticion, redMineManager, issue);
        
        // Si llega la categoría, la asignamos a la petición        
        setCategoria(idProyecto, peticion, redMineManager, issue);
        
        // Si llega petición padre, la establecemos
        if (peticion.getIdPeticionPadre() != null) {
        	issue.setParentId(peticion.getIdPeticionPadre());
        }

        // Si llega con usuario asignado, lo establecemos
        if (peticion.getIdAsignado() != null) {
        	issue.setAssigneeId(peticion.getIdAsignado());
        }
        
        // Si llega con prioridad, la establecemos        
        if (peticion.getIdPrioridad() != null) {
        	issue.setPriorityId(peticion.getIdPrioridad());
        }
        
        Issue createdIssue;
		try {
			createdIssue = issue.create();
			result = PeticionImpl.builder()
					.asunto(createdIssue.getSubject())
					.descripcion(createdIssue.getDescription())
					.fechaInicio(createdIssue.getStartDate())
					.fechaFin(createdIssue.getDueDate())
					.tipoPeticion(createdIssue.getTracker().getId())
					.id(createdIssue.getId())
					.idAsignado(createdIssue.getAssigneeId())
					.idPeticionPadre(createdIssue.getParentId())
					.idPrioridad(createdIssue.getPriorityId())
					.build();
			//TODO Copiar asignado y categoría, así como peticion padre y prioridad			
		} catch (RedmineException e) {
			log.error("Error al crear la petición en el RedMine destino: {}",  uri, e);
		}
		
		log.info("Se ha creado la petición {} a partir de la petición {} con el siguiente asunto {}", result.getId(), peticion.getId(), result.getAsunto());
		
		return result;
	}


	/**
	 * Sets the categoria.
	 *
	 * @param idProyecto the id proyecto
	 * @param peticion the peticion
	 * @param redMineManager the red mine manager
	 * @param issue the issue
	 */
	private void setCategoria(String idProyecto, Peticion peticion, RedmineManager redMineManager, Issue issue) {
		if (peticion.getCategoria() != null) {
        	Iterator<IssueCategory> categories;
        	try {
				categories = redMineManager.getIssueManager().getCategories(issue.getProjectId()).iterator();
				boolean encontrado = false;
	        	while(categories.hasNext() && !encontrado) {
	        		IssueCategory category = categories.next();
	        		if (category.getId().equals(peticion.getCategoria())) {
	        			issue.setCategory(category);
	        			encontrado = true;
	        		}
	        	}
			} catch (RedmineException e) {
				log.error("Error al obtener las categorías de Redmine para el proyecto " + idProyecto, e);
			}
        }
	}


	/**
	 * Sets the tipo peticion.
	 *
	 * @param peticion the peticion
	 * @param redMineManager the red mine manager
	 * @param issue the issue
	 */
	private void setTipoPeticion(Peticion peticion, RedmineManager redMineManager, Issue issue) {
		if (peticion.getTipoPeticion() != null) {
        	Iterator<Tracker> trackers;
			try {
				trackers = redMineManager.getIssueManager().getTrackers().iterator();
				boolean encontrado = false;
	        	while(trackers.hasNext() && !encontrado) {
	        		Tracker tracker = trackers.next();
	        		if (tracker.getId().equals(peticion.getTipoPeticion())) {
	        			issue.setTracker(tracker);
	        			encontrado = true;
	        		}
	        	}
			} catch (RedmineException e) {
				log.error("Error al obtener los trackers de Redmine", e);
			}
        }
	}	


}
