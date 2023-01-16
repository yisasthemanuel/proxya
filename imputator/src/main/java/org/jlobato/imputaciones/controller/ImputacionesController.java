package org.jlobato.imputaciones.controller;

import java.io.IOException;
import java.util.List;

import org.jlobato.imputaciones.config.RedMineConfiguracion;
import org.jlobato.imputaciones.model.CargaImputaciones;
import org.jlobato.imputaciones.model.Imputacion;
import org.jlobato.imputaciones.model.RedMine;
import org.jlobato.imputaciones.repository.impl.ImputacionExcelReader;
import org.jlobato.imputaciones.service.BackupImputacionesService;
import org.jlobato.imputaciones.service.ImputacionService;
import org.jlobato.imputaciones.service.RedMineService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * The Class ImputacionesController.
 */
@Controller
@RequestMapping("/imputaciones")

/** The Constant log. */

/** The Constant log. */
@Slf4j
public class ImputacionesController {
	
	private static final String INDEX_VIEW = "index";

	/** The config. */
	@Autowired
	RedMineConfiguracion config;
	
	/** The seta service. */
	@Autowired
	ImputacionService imputacionService;
	
	/** The seta backup service. */
	@Autowired
	BackupImputacionesService redmineBackupService;
	
	/** The target red mine service. */
	@Autowired
	RedMineService targetRedMineService;
	
	/**
	 * Main.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping({"", "/", INDEX_VIEW})
	public String main(Model model) {
		updateModelIndex(model);
		return INDEX_VIEW;
	}
	
	/**
	 * Nueva carga imputaciones.
	 *
	 * @param model the model
	 * @param targetRedMine the target red mine
	 * @param fichImputaciones the fich imputaciones
	 * @return the string
	 * @throws RedmineException the redmine exception
	 * @throws IOException 
	 */
	@PostMapping("nuevaCarga")
	public String nuevaCargaImputaciones(Model model, Integer targetRedMine, MultipartFile fichImputaciones) throws RedmineException, IOException {
		StringBuilder validationMessages = new StringBuilder();
		
		//Si no llega el fichero
		if (fichImputaciones == null) {
			validationMessages.append("No se ha seleccionado ningún archivo con las imputaciones a realizar. ");
		}
		
		//Si no se ha seleccionado un redmine de destino
	    RedMine redmine = targetRedMineService.getRedMine(targetRedMine);
		if (redmine == null) {
			validationMessages.append("No has seleccionado RedMine donde hacer las imputaciones. ");
		}
		
	    // Esto debe extraerse de algún mecanismo externo (hoja excel, csv, etc)
		// Si el fichero no tiene el formato correcto o está vacío
		if (fichImputaciones != null) {			
			ImputacionExcelReader reader = new ImputacionExcelReader();
		    List<Imputacion> imputaciones = reader.getImputaciones(fichImputaciones.getInputStream());	    
		    if (imputaciones.isEmpty()) {
				validationMessages.append("No se ha proporcionado un fichero, no hay imputaciones o no se han podido obtener del fichero proporcionado. ");
		    }
		    
			if (validationMessages.length() > 0) {
				model.addAttribute("testDone", validationMessages.toString());
			    //REFACTORIZAR
			    model.addAttribute("selectedRedMine", targetRedMine);
			    updateModelIndex(model);
			    return INDEX_VIEW;
			}
		    
		    CargaImputaciones backup = redmineBackupService.doBackup(redmine, imputaciones);
		    
		    if (log.isInfoEnabled()) log.info("Backup con id {} realizado el {}", backup.getId(), backup.getFechaEjecucion());
		    
		    //Realizamos las imputaciones
		    imputacionService.doImputaciones(redmine, imputaciones);
		    
		    //Mandamos info al log
		    if (log.isDebugEnabled()) log.debug(imputaciones.toString());
		    
		    //Mandamos información para la vista
		    model.addAttribute("testDone", imputaciones);
		}
	    
	    //REFACTORIZAR
	    model.addAttribute("selectedRedMine", targetRedMine);
	    updateModelIndex(model);
	    
		return INDEX_VIEW;
	}

	/**
	 * Update model index.
	 *
	 * @param model the model
	 */ 
	private void updateModelIndex(Model model) {
		model.addAttribute("imputacionesProperties", config.getRedMineProperties());
	    model.addAttribute("redmines", targetRedMineService.getRedMinesAvailables());
	    model.addAttribute("seleccioneOpcionText", "Por favor, seleccione una opción..."); //i18n
	}
	
	/**
	 * Restore.
	 *
	 * @param model the model
	 * @return the string
	 * @throws RedmineException the redmine exception
	 */
	@GetMapping("restore")
	public String restore(Model model, @RequestParam(required = false) String id) throws RedmineException {
		if (id != null) {
			redmineBackupService.doRestore(id);
		}
		model.addAttribute("cargasDisponibles", redmineBackupService.getCargasDisponibles());
		return "backups";
	}

}
