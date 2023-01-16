package org.jlobato.imputaciones.service;

import java.util.List;

import org.jlobato.imputaciones.model.CargaImputaciones;
import org.jlobato.imputaciones.model.Imputacion;
import org.jlobato.imputaciones.model.RedMine;

// TODO: Auto-generated Javadoc
/**
 * The Interface BackupImputacionesService.
 *
 * @author Jesús Manuel Pérez
 */
public interface BackupImputacionesService {
	
	/**
	 * Do backup.
	 *
	 * @param redmine the redmine
	 * @param imputacionesCarga the imputaciones carga
	 * @return the carga imputaciones SETA
	 */
	public CargaImputaciones doBackup(RedMine redmine, List<Imputacion> imputacionesCarga);
	
	/**
	 * Do restore.
	 *
	 * @param idBackup the id backup
	 */
	public void doRestore(String idBackup);
	
	public List<CargaImputaciones> getCargasDisponibles();

}
