package org.jlobato.imputaciones.service.impl;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.http.client.HttpClient;
import org.jlobato.imputaciones.config.RedMineProperties;
import org.jlobato.imputaciones.model.Estimacion;
import org.jlobato.imputaciones.model.Imputacion;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.Peticion;
import org.jlobato.imputaciones.model.RedMine;
import org.jlobato.imputaciones.model.impl.EstimacionImpl;
import org.jlobato.imputaciones.model.impl.ImputacionImpl;
import org.jlobato.imputaciones.model.impl.PeticionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taskadapter.redmineapi.IssueManager;
import com.taskadapter.redmineapi.NotFoundException;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;
import com.taskadapter.redmineapi.bean.CustomField;
import com.taskadapter.redmineapi.bean.Issue;
import com.taskadapter.redmineapi.bean.IssueRelation;
import com.taskadapter.redmineapi.bean.TimeEntry;

import lombok.extern.slf4j.Slf4j;

/** The Constant log. */
@Slf4j
@Component
public class RedMineDriver {
	
	private static final String TEXTO_REQUISITO_SEGURIDAD = "Para realizar este desarrollo se deben de tener en cuenta los siguientes requisitos de seguridad: https://seta.ceceu.junta-andalucia.es/projects/gestion-integral-de-ss-ii/wiki/REQUISITOS_SEGURIDAD_RECURRENTES_DESARROLLO";

	private static final String SEGURIDAD_CADENA_IDENTIFICATIVA = "/REQUISITOS_SEGURIDAD_RECURRENTES_DESARROLLO";

	/** The Constant ERROR_EN_SETA. */
	private static final String ERROR_EN_SETA = "[{}] -> Petición con ID [{}] ha dado un error en SETA.";

	/** The http client. */
	@Autowired
	HttpClient httpClient;

	/** The issue manager SETA. */
	IssueManager issueManagerDefault;

	/** The imputaciones fields. */
	@Autowired
	Map<String, String> imputacionesFields;
	
	/** The seta properties. */
	@Autowired
	RedMineProperties setaProperties;

	/** The formateador horas. */
	@Autowired
	NumberFormat formateadorHoras;
	
	/** The formateador fecha. */
	@Autowired
	SimpleDateFormat formateadorFecha;
	
	/** The formateador fecha restauracion. */
	@Autowired
	SimpleDateFormat formateadorFechaRestauracion;
	
	@Autowired
	SimpleDateFormat formateadorFechaImputaciones;
	
	@Autowired
	MessageFormat formateadorTextos;

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
	
	public void tiempoDedicado(String redmineURI, String apiKey, Integer issueId, Date fechaImputacion, Float horas, Integer activityId, String comentario) {
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(redmineURI, apiKey, httpClient);
		
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
		log.info("Imputación realizada por {} el día {} en la petición {}", apiKey, formateadorFechaImputaciones.format(fechaImputacion), issueId);

	}
	
	public void creaRelacion(String redmineURI, String apiKey, Integer sourceId, Integer targetId) {
		String relationText = "relates";
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(redmineURI, apiKey, httpClient);
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
		//TODO Factoría
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
	 * 
	 * @param redmine
	 * @param persona
	 * @param issueId
	 * @param horas
	 */
	public void tiempoEstimado(RedMine redmine, Persona persona, Integer issueId, Float horas) {
		this.tiempoEstimado(redmine, EstimacionImpl.builder().id(issueId).persona(persona).horas(horas).build());
	}

	/**
	 * 
	 * @param redmine
	 * @param estimacion
	 */
	public void tiempoEstimado(RedMine redmine, Estimacion estimacion) {
		log.info("{} está estimando la HU {} en {} horas", estimacion.getPersona().getNombreCompleto(), estimacion.getId(), estimacion.getHoras());
		RedmineManager redMineManager = RedmineManagerFactory.createWithApiKey(redmine.getUri(), estimacion.getPersona().getApiKey(), httpClient);
		IssueManager issueManager = redMineManager.getIssueManager();
		try {
			Issue hu = issueManager.getIssueById(estimacion.getId());
			hu.setEstimatedHours(estimacion.getHoras());
			hu.update();
		} catch (RedmineException e) {
			log.error("Error al obtener la petición {}",  estimacion.getId(), e);
		}
		
		log.info("Estimación realizada con éxito");
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
	 * @return the peticion
	 */
	public Peticion creaPeticion(String uri, String idProyecto, String apiKey, String asunto, String descripcion, Date fechaInicio, Date fechaFin) {
		Peticion result = null;
		//
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
        
        Issue createdIssue;
		try {
			createdIssue = issue.create();
			result = PeticionImpl.builder()
					.asunto(asunto)
					.descripcion(descripcion)
					.fechaInicio(fechaInicio)
					.fechaFin(fechaFin)
					.id(createdIssue.getId()).build();
		} catch (RedmineException e) {
			log.error("Error al crear la petición en el RedMine destino: {}",  uri, e);
		}
		
		return result;
	}	


}
