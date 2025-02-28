package org.jlobato.imputaciones.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jlobato.imputaciones.model.ImputacionIndividual;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.Proyecto;
import org.jlobato.imputaciones.model.RedMine;
import org.jlobato.imputaciones.repository.PersonaRepository;
import org.jlobato.imputaciones.repository.impl.ImputacionIndividualExcelReader;
import org.jlobato.imputaciones.service.EstimacionService;
import org.jlobato.imputaciones.service.ImputacionService;
import org.jlobato.imputaciones.service.PeticionService;
import org.jlobato.imputaciones.service.ProyectoService;
import org.jlobato.imputaciones.service.RedMineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.taskadapter.redmineapi.RedmineException;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class ImputacionesIndividualesController.
 */
@Controller
@RequestMapping("/gesproy")

/** The Constant log. */

/** The Constant log. */
@Slf4j
public class ImputacionesIndividualesController {
	
	/** The Constant ALMOHADILLA_ATTR. */
	private static final String ALMOHADILLA_ATTR = "######";

	/** The Constant TARGET_RED_MINE_ATTR. */
	private static final String TARGET_RED_MINE_ATTR = "targetRedMine";

	/** The Constant VALIDACIONES_ATTR. */
	private static final String VALIDACIONES_ATTR = "validaciones";

	/** The Constant SELECT_AN_OPTION_ATTR. */
	private static final String SELECT_AN_OPTION_ATTR = "Por favor, seleccione una opción...";

	/** The Constant SELECCIONE_OPCION_TEXT_ATTR. */
	private static final String SELECCIONE_OPCION_TEXT_ATTR = "seleccioneOpcionText";

	/** The Constant REDMINES_ATTR. */
	private static final String REDMINES_ATTR = "redmines";
	
	private static final String PROJECTS_ATTR = "proyectos";

	private static final String PERSONAS_ATTR = "personas";
	
	/** The reader. */
	ImputacionIndividualExcelReader reader;
	
	/** The default persona. */
	Persona defaultPersona;

	/** The target red mine service. */
	RedMineService targetRedMineService;
	
	/** The imputacion service. */
	ImputacionService imputacionService;
	
	/** The persona repository. */
	PersonaRepository personaRepository;
	
	/** The project service. */
	ProyectoService projectService;

	/** The peticion service. */
	PeticionService peticionService;

	/** The estimacion service. */
	EstimacionService estimacionService;

	/** The Constant DEFAULT_REDMINE. */
	private static final Integer DEFAULT_REDMINE = 3;
	
	/** The Constant INDEX_VIEW. */
	private static final String INDEX_VIEW = "indexindv";
	
	/**
	 * Instantiates a new imputaciones individuales controller.
	 *
	 * @param reader the reader
	 * @param defaultPersona the default persona
	 */
	public ImputacionesIndividualesController(ImputacionIndividualExcelReader reader, @Qualifier("getDefaultPersona") Persona defaultPersona) {
		super();
		this.reader = reader;
		this.defaultPersona = defaultPersona;
	}
	
	/**
	 * Sets the target red mine service.
	 *
	 * @param targetRedMineService the new target red mine service
	 */
	@Autowired
	private void setTargetRedMineService(RedMineService targetRedMineService) {
		this.targetRedMineService = targetRedMineService;
	}
	
	/**
	 * Sets the persona repository.
	 *
	 * @param personaRepository the new persona repository
	 */
	@Autowired
	private void setPersonaRepository(PersonaRepository personaRepository) {
		this.personaRepository = personaRepository;
	}

	/**
	 * Sets the project service.
	 *
	 * @param projectService the new project service
	 */
	@Autowired
	private void setProjectService(ProyectoService projectService) {
		this.projectService = projectService;
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
	 * Sets the imputacion service.
	 *
	 * @param imputacionService the new imputacion service
	 */
	@Autowired
	private void setImputacionService(ImputacionService imputacionService) {
		this.imputacionService = imputacionService;
	}
	
	/**
	 * Main.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(INDEX_VIEW)
	public String main(Model model) {
	    model.addAttribute(REDMINES_ATTR, targetRedMineService.getRedMinesAvailables());
	    model.addAttribute(SELECCIONE_OPCION_TEXT_ATTR, SELECT_AN_OPTION_ATTR);
		return INDEX_VIEW;
	}

	
	/**
	 * Carga imputaciones.
	 *
	 * @param model the model
	 * @param targetRedMine the target red mine
	 * @param fichImputaciones the fich imputaciones
	 * @return the string
	 * @throws RedmineException the redmine exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostMapping("cargaImputaciones")
	public String cargaImputaciones(Model model, Integer targetRedMine, MultipartFile fichImputaciones) throws RedmineException, IOException {
		
		List<String> validationMessages = new ArrayList<>();
		
		//No hay imputaciones
		if (fichImputaciones == null) {
			throw new IllegalArgumentException("No se han subido las imputaciones");
		}
			
		//Si no se ha seleccionado un redmine de destino
	    RedMine redmine = targetRedMineService.getRedMine(targetRedMine);
		if (redmine == null) {
			redmine = targetRedMineService.getRedMine(DEFAULT_REDMINE);
		}
		
	    List<ImputacionIndividual> imputaciones = reader.getImputaciones(fichImputaciones.getInputStream());	    
	    if (imputaciones.isEmpty()) {
			validationMessages.add("No se ha proporcionado un fichero, no hay imputaciones o no se han podido obtener del fichero proporcionado. ");
	    }
	    
		if (!validationMessages.isEmpty()) {
			model.addAttribute(VALIDACIONES_ATTR, validationMessages.toString());
		    model.addAttribute(REDMINES_ATTR, targetRedMineService.getRedMinesAvailables());
		    model.addAttribute(TARGET_RED_MINE_ATTR, targetRedMine);
		    model.addAttribute(SELECCIONE_OPCION_TEXT_ATTR, SELECT_AN_OPTION_ATTR);
		    return INDEX_VIEW;
		}
	    
		log.info(ALMOHADILLA_ATTR);
		log.info("Iniciamos el proceso de imputación en la HU de mantenimiento");
		log.info(ALMOHADILLA_ATTR);
		
		float totalHorasImputadas = 0;
	    //Realizamos las imputaciones
		Iterator<ImputacionIndividual> iterador = imputaciones.iterator();
		while(iterador.hasNext()) {
			ImputacionIndividual imputacionIndividual = iterador.next();
		    imputacionService.tiempoDedicado(redmine, imputacionIndividual);
		    totalHorasImputadas += imputacionIndividual.getHoras();
		}
		
		log.info(ALMOHADILLA_ATTR);
		log.info("Fin del proceso de imputación la HU de mantenimiento. Se han realizado {} imputaciones que dan un total de {} horas",
				imputaciones.size(), totalHorasImputadas);
		log.info(ALMOHADILLA_ATTR);
		
	    
	    //Mandamos info al log
	    if (log.isDebugEnabled()) log.debug(imputaciones.toString());
	    
	    //Mandamos información para la vista
	    model.addAttribute(REDMINES_ATTR, targetRedMineService.getRedMinesAvailables());
	    model.addAttribute(TARGET_RED_MINE_ATTR, targetRedMine);
	    model.addAttribute(SELECCIONE_OPCION_TEXT_ATTR, SELECT_AN_OPTION_ATTR); //i18n
		return INDEX_VIEW;
	}
	
	/**
	 * Relaciona peticiones.
	 *
	 * @param model the model
	 * @param targetRedMine the target red mine
	 * @param huMantenimiento the hu mantenimiento
	 * @param peticionesRelacionadas the peticiones relacionadas
	 * @return the string
	 * @throws RedmineException the redmine exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostMapping("relacionaPeticiones")
	public String relacionaPeticiones(Model model, Integer targetRedMine, String huMantenimiento, String peticionesRelacionadas) throws RedmineException, IOException {
		
		List<String> validationMessages = new ArrayList<>();
		
		//No hay imputaciones
		if (huMantenimiento == null) {
			validationMessages.add("No has especificado HU");
		}
		
		Integer source = null;
		try {
			source = Integer.valueOf(huMantenimiento);			
		} catch (Exception e) {
			validationMessages.add("La HU destino debe ser un número [" + huMantenimiento + "]");
		}
			
		//Si no se ha seleccionado un redmine de destino
	    RedMine redmine = targetRedMineService.getRedMine(targetRedMine);
		if (redmine == null) {
			redmine = targetRedMineService.getRedMine(DEFAULT_REDMINE);
		}
		
		if (peticionesRelacionadas == null) {
			validationMessages.add("No has indicado la lista de peticiones que hay que relacionar");
		}
		
		Integer[] targets = new Integer[] {};
		if (peticionesRelacionadas != null) {
			targets = extraePeticiones(peticionesRelacionadas, validationMessages);
		}
		
		if (!validationMessages.isEmpty()) {
			model.addAttribute(VALIDACIONES_ATTR, validationMessages.toString());
		    model.addAttribute(REDMINES_ATTR, targetRedMineService.getRedMinesAvailables());
		    model.addAttribute(TARGET_RED_MINE_ATTR, targetRedMine);
		    model.addAttribute(SELECCIONE_OPCION_TEXT_ATTR, SELECT_AN_OPTION_ATTR); //i18n
		    return INDEX_VIEW;
		}

		log.info(ALMOHADILLA_ATTR);
		log.info("Iniciamos el proceso de relación de peticiones en la HU de mantenimiento");
		log.info(ALMOHADILLA_ATTR);
		
		log.info("Número de peticiones a relacionar {}", targets.length);
		
		for (int i = 0; i < targets.length; i++) {
			imputacionService.creaRelacion(redmine, defaultPersona, source, targets[i]);
			log.info("Relacionados: En RedMine {} por la persona {} la petición {} con la petición {}",
					redmine.getUri(), defaultPersona.getNombreCompleto(), source, targets[i]);
		}

		log.info(ALMOHADILLA_ATTR);
		log.info("Fin del proceso de relación de peticiones. Se han relacionado {} peticiones a la petición {}",
				targets.length, source);
		log.info(ALMOHADILLA_ATTR);
		
		validationMessages.clear();
		validationMessages.add("Peticiones relacionadas correctamente");
		
		if (!validationMessages.isEmpty()) {
			model.addAttribute(VALIDACIONES_ATTR, validationMessages.toString());
		}
		
	    //Mandamos información para la vista
	    model.addAttribute(REDMINES_ATTR, targetRedMineService.getRedMinesAvailables());
	    model.addAttribute(TARGET_RED_MINE_ATTR, targetRedMine);
	    model.addAttribute(SELECCIONE_OPCION_TEXT_ATTR, SELECT_AN_OPTION_ATTR); //i18n
		return INDEX_VIEW;
	}


	/**
	 * Extrae peticiones.
	 *
	 * @param peticionesRelacionadas the peticiones relacionadas
	 * @param validationMessages the validation messages
	 * @return the integer[]
	 */
	private Integer[] extraePeticiones(String peticionesRelacionadas, List<String> validationMessages) {
		Integer[] result = {};
		String[] listaPeticiones = peticionesRelacionadas.split(",");
		
		if (listaPeticiones.length > 0) {
			result = new Integer[listaPeticiones.length];
		}
		
		for (int i = 0; i < listaPeticiones.length; i++) {
			try {
				result[i] = Integer.valueOf(listaPeticiones[i].replaceAll("\\s+", ""));
			} catch (Exception e) {
				validationMessages.add("El id de la petición no es un número: " + listaPeticiones[i]);
			}
		}
		
		return result;
	}
	
	/**
	 * Copia imputaciones sprint.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("indexcopia")
	public String indexCopiaImputaciones(Model model) {
		log.debug("targetRedmineService: {}", this.targetRedMineService);
		log.debug("imputacionService: {}", this.imputacionService);
		log.debug("personaRepository: {}", this.personaRepository);
		log.debug("projectService: {}", this.projectService);
		log.debug("peticionService: {}", this.peticionService);
		log.debug("estimacionService: {}", this.estimacionService);
		
	    model.addAttribute(REDMINES_ATTR, targetRedMineService.getRedMinesAvailables());
	    model.addAttribute(PROJECTS_ATTR, projectService.getProyectoByIdentificador("2404_v-edu-a-educamosclm-s-mantenimiento"));
	    model.addAttribute(PERSONAS_ATTR, personaRepository.getAllPersonas());
	    model.addAttribute(SELECCIONE_OPCION_TEXT_ATTR, SELECT_AN_OPTION_ATTR);
		
		return "copiahus";
	}
	
	/**
	 * Copia imputaciones.
	 *
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 * @param targetRedMine the target red mine
	 * @param selectedProject the selected project
	 * @param personas the personas
	 * @return the string
	 */
	@PostMapping("copiaImputaciones")
	public String copiaImputaciones(@RequestParam("fechaInicio") String fechaInicio,
			@RequestParam("fechaFin") String fechaFin,
			@RequestParam("targetRedMine") Integer targetRedMine,
			@RequestParam("selectedProject") String selectedProject,
			@RequestParam("personas") List<String> personas) {
		
		Date fechaInicioDate = null;
		Date fechaFinDate = null;
		try {
			//TODO Mejorar la forma de utilizar el DateTimeFormatter
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			fechaInicioDate = java.sql.Date.valueOf(LocalDate.parse(fechaInicio, formatter));
			fechaFinDate = java.sql.Date.valueOf(LocalDate.parse(fechaFin, formatter));
		} catch (Exception e) {
			log.error("Fecha de inicio = {} - Fecha de fin = {}", fechaInicio, fechaFin);			
			log.error("Error al obtener las fechas de inicio y/o fin de las imputaciones a copiar", e);
		}
		
		// Si hemos conseguido tener fechas de inicio y fin
		if (fechaInicioDate != null && fechaFinDate != null) {
			RedMine redmine = targetRedMineService.getRedMine(targetRedMine);
			Proyecto proyecto = projectService.getProyectoByIdentificador(selectedProject); //"2404_v-edu-a-educamosclm-s-mantenimiento"
			log.info("Comienza el proceso de copia de imputaciones para las siguientes personas {}", personas);
			for (String identificadorPersona : personas) {
				Persona persona = personaRepository.getPersona(identificadorPersona); // "jcmt01" "rrhg20"
				// Iteramos por las personas implicadas y copiamos sus imputaciones
				imputacionService.copiaImputaciones(fechaInicioDate, fechaFinDate, redmine, proyecto, persona);
			}

			log.info("Proceso de copia de imputaciones finalizado con éxito");
		}

		return "copiahus";
	}
}
