package org.jlobato.imputaciones.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jlobato.imputaciones.model.CargaImputaciones;
import org.jlobato.imputaciones.model.Imputacion;
import org.jlobato.imputaciones.model.RedMine;
import org.jlobato.imputaciones.model.impl.CargaImputacionesImpl;
import org.jlobato.imputaciones.repository.CargaImputacionesRepository;
import org.jlobato.imputaciones.service.BackupImputacionesService;
import org.jlobato.imputaciones.service.CargaImputacionesIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/** The Constant log. */

/** The Constant log. */
@Slf4j
@Service
public class BackupImputacionesServiceImpl implements BackupImputacionesService {
	
	/** The seta driver. */
	@Autowired
	RedMineDriver redmineDriver;
	
	/** The id generator. */
	@Autowired
	CargaImputacionesIDGenerator idGenerator;
	
	/** The seta carga repo. */
	@Autowired
	CargaImputacionesRepository cargasRepo;
	
	/**
	 * Do backup.
	 *
	 * @param redmine the redmine
	 * @param imputacionesCarga the imputaciones carga
	 * @return the carga imputaciones SETA
	 */
	@Override
	public CargaImputaciones doBackup(RedMine redmine, List<Imputacion> imputacionesCarga) {
		//Generamos un id de carga
		String id = idGenerator.getNewID();
		//Fecha de la carga
		Date rightNow = new Date();
		
	    log.info("Backup iniciado a las {} con ID '{}'", rightNow, id);
		
		List<Imputacion> imputacionesBackup = new ArrayList<>();
		
		//Recorremos las imputaciones a realizar para obtener el valor actual
		for (Imputacion imputacion: imputacionesCarga) {
			imputacionesBackup.add(redmineDriver.getImputacionPrevia(redmine, imputacion));
		}
		
		//Creamos el objeto backup de las imputaciones
		//TODO Factoría
		CargaImputaciones result = CargaImputacionesImpl.builder()
				.id(id)
				.fechaEjecucion(rightNow)
				.imputaciones(imputacionesBackup)
				.destino(redmine)
				.build();
		
		//Se delega en el repository la persistencia de la carga
		cargasRepo.addCarga(result);
		
	    log.info("Backup Finalizado {}", result);
		
		return result;
	}
	
	/**
	 * Do restore.
	 *
	 * @param idBackup the id backup
	 */
	@Override
	public void doRestore(String idBackup) {
		CargaImputaciones carga = cargasRepo.getCarga(idBackup);
		if (carga != null) {
			log.info("Comenzamos la restauración de la carga con id {}", idBackup);
			//Comenzamos la restauración
			for (Imputacion imputacion: carga.getImputaciones()) {
				redmineDriver.setImputacion(carga.getDestino(), imputacion, carga.getFechaEjecucion());
			}
			log.info("Finalizada la restauración de la carga con id {}", idBackup);
		}
	}

	@Override
	public List<CargaImputaciones> getCargasDisponibles() {
		//Falta darle el criterio de ordenación
		return cargasRepo.getCargasDisponibles();
	}
}
