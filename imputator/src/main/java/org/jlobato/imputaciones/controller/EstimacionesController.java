package org.jlobato.imputaciones.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jlobato.imputaciones.model.Estimacion;
import org.jlobato.imputaciones.model.RedMine;
import org.jlobato.imputaciones.repository.impl.EstimacionExcelReader;
import org.jlobato.imputaciones.service.EstimacionService;
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
public class EstimacionesController {
	
	/** The target red mine service. */
	@Autowired
	RedMineService targetRedMineService;
	
	@Autowired
	EstimacionService estimacionService;
	
	@Autowired
	EstimacionExcelReader reader;
	
	private static final Integer DEFAULT_REDMINE = 3;
	
	private static final String INDEX_VIEW = "indexest";
	
	@GetMapping(INDEX_VIEW)
	public String main(Model model) {
		return INDEX_VIEW;
	}

	
	@PostMapping("cargaEstimaciones")
	public String cargaImputaciones(Model model, Integer targetRedMine, MultipartFile fichEstimaciones) throws RedmineException, IOException {
		
		List<String> validationMessages = new ArrayList<>();
		
		//No hay imputaciones
		if (fichEstimaciones == null) {
			throw new IllegalArgumentException("No se han subido las estimaciones");
		}
			
		//Si no se ha seleccionado un redmine de destino
	    RedMine redmine = targetRedMineService.getRedMine(targetRedMine);
		if (redmine == null) {
			redmine = targetRedMineService.getRedMine(DEFAULT_REDMINE);
		}
		
	    List<Estimacion> estimaciones = reader.getEstimaciones(fichEstimaciones.getInputStream());	    
	    if (estimaciones.isEmpty()) {
			validationMessages.add("No se ha proporcionado un fichero, no hay imputaciones o no se han podido obtener del fichero proporcionado. ");
	    }
	    
		if (!validationMessages.isEmpty()) {
			model.addAttribute("validaciones", validationMessages.toString());
		    return INDEX_VIEW;
		}
	    
	    //Realizamos las imputaciones
		Iterator<Estimacion> iterador = estimaciones.iterator();
		while(iterador.hasNext()) {
		    estimacionService.tiempoEstimado(redmine, iterador.next());
		}
	    
	    //Mandamos info al log
	    if (log.isDebugEnabled()) log.debug(estimaciones.toString());
	    
	    //Mandamos información para la vista
		return INDEX_VIEW;
	}
	

}
