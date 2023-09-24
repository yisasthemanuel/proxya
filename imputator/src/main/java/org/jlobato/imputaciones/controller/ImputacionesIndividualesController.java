package org.jlobato.imputaciones.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jlobato.imputaciones.model.ImputacionIndividual;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.RedMine;
import org.jlobato.imputaciones.repository.impl.ImputacionIndividualExcelReader;
import org.jlobato.imputaciones.service.ImputacionService;
import org.jlobato.imputaciones.service.RedMineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.taskadapter.redmineapi.RedmineException;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/gesproy")
@Slf4j
public class ImputacionesIndividualesController {
	/** The target red mine service. */
	@Autowired
	RedMineService targetRedMineService;
	
	@Autowired
	ImputacionService imputacionService;
	
	@Autowired
	ImputacionIndividualExcelReader reader;
	
	@Autowired
	Persona defaultPersona;
	
	private static final Integer DEFAULT_REDMINE = 3;
	
	private static final String INDEX_VIEW = "indexindv";
	
	@GetMapping(INDEX_VIEW)
	public String main(Model model) {
		return INDEX_VIEW;
	}

	
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
			model.addAttribute("validaciones", validationMessages.toString());
		    return INDEX_VIEW;
		}
	    
		log.info("######");
		log.info("Iniciamos el proceso de imputación en la HU de mantenimiento");
		log.info("######");
		
		float totalHorasImputadas = 0;
	    //Realizamos las imputaciones
		Iterator<ImputacionIndividual> iterador = imputaciones.iterator();
		while(iterador.hasNext()) {
			ImputacionIndividual imputacionIndividual = iterador.next();
		    imputacionService.tiempoDedicado(redmine, imputacionIndividual);
		    totalHorasImputadas += imputacionIndividual.getHoras();
		}
		
		log.info("######");
		log.info("Fin del proceso de imputación la HU de mantenimiento. Se han realizado {} imputaciones que dan un total de {} horas",
				imputaciones.size(), totalHorasImputadas);
		log.info("######");
		
	    
	    //Mandamos info al log
	    if (log.isDebugEnabled()) log.debug(imputaciones.toString());
	    
	    //Mandamos información para la vista
		return INDEX_VIEW;
	}
	
	@PostMapping("relacionaPeticiones")
	public String relacionaPeticiones(Model model, Integer targetRedMine, String huMantenimiento, String peticionesRelacionadas) throws RedmineException, IOException {
		
		List<String> validationMessages = new ArrayList<>();
		
		//No hay imputaciones
		if (huMantenimiento == null) {
			validationMessages.add("No has especificado HU");
		}
		
		Integer source = null;
		try {
			source = new Integer(huMantenimiento);			
		} catch (Exception e) {
			validationMessages.add("La HU destino debe ser un número");
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
			model.addAttribute("validaciones", validationMessages.toString());
		    return INDEX_VIEW;
		}

		log.info("######");
		log.info("Iniciamos el proceso de relación de peticiones en la HU de mantenimiento");
		log.info("######");
		
		log.info("Número de peticiones a relacionar {}", targets.length);
		
		for (int i = 0; i < targets.length; i++) {
			imputacionService.creaRelacion(redmine, defaultPersona, source, targets[i]);
			log.info("Relacionados: En RedMine {} por la persona {} la petición {} con la petición {}",
					redmine.getUri(), defaultPersona.getNombreCompleto(), source, targets[i]);
		}

		log.info("######");
		log.info("Fin del proceso de relación de peticiones. Se han relacionado {} peticiones a la petición {}",
				targets.length, source);
		log.info("######");
		
		validationMessages.clear();
		validationMessages.add("Peticiones relacionadas correctamente");
		
		if (!validationMessages.isEmpty()) {
			model.addAttribute("validaciones", validationMessages.toString());
		}
		
	    //Mandamos información para la vista
		return INDEX_VIEW;
	}


	/**
	 * 
	 * @param peticionesRelacionadas
	 * @param validationMessages
	 * @return
	 */
	private Integer[] extraePeticiones(String peticionesRelacionadas, List<String> validationMessages) {
		Integer[] result = {};
		String[] listaPeticiones = peticionesRelacionadas.split(",");
		
		if (listaPeticiones.length > 0) {
			result = new Integer[listaPeticiones.length];
		}
		
		for (int i = 0; i < listaPeticiones.length; i++) {
			try {
				result[i] = new Integer(listaPeticiones[i].replaceAll("\\s+", ""));
			} catch (Exception e) {
				validationMessages.add("El id de la petición no es un número: " + listaPeticiones[i]);
			}
		}
		
		return result;
	}
	

}
