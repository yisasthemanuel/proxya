package org.jlobato.imputaciones.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.jlobato.imputaciones.model.CargaImputaciones;
import org.jlobato.imputaciones.repository.CargaImputacionesRepository;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/** The Constant log. */

/** The Constant log. */
@Slf4j
@Repository
public class CargaImputacionesRepositoryFileImpl implements CargaImputacionesRepository {

	/**
	 * Adds the SETA carga.
	 *
	 * @param carga the carga
	 */
	@Override
	public void addCarga(CargaImputaciones carga) {
		try (FileOutputStream fos = new FileOutputStream("./carga" + carga.getId() + ".set");
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(carga);
		} catch (IOException e) {
			log.error("Error guardando la carga con id '{}'", carga.getId(), e);

		}
		
	}

	/**
	 * Gets the SETA carga.
	 *
	 * @param id the id
	 * @return the SETA carga
	 */
	@Override
	public CargaImputaciones getCarga(String id) {
		CargaImputaciones result = null;
		try (FileInputStream fis = new FileInputStream("./carga" + id + ".set");
				ObjectInputStream ios = new ObjectInputStream(fis)) {
			result = (CargaImputaciones)ios.readObject();
		} catch (IOException | ClassNotFoundException e) {
			log.error("Error recuperando la carga con id '{}'", id, e);
		}
		return result;
	}

	/**
	 * Gets the cargas disponibles.
	 *
	 * @return the cargas disponibles
	 */
	@Override
	public List<CargaImputaciones> getCargasDisponibles() {
		List<CargaImputaciones> result = new ArrayList<>();
		//Buscamos en el directorio por defecto y hacemos un findfile que tengan el prefijo de carga.
		Collection<File> cargas = FileUtils.listFiles(new File("."), new WildcardFileFilter("carga*"), null);
		
		int filesNumber = cargas.size();
		
		//Por cada fichero, obtenemos el objeto carga correspondiente
		for(File fichero: cargas) {
			// TODO refactorizar			
			try (FileInputStream fis = new FileInputStream(fichero);
					ObjectInputStream ios = new ObjectInputStream(fis)) {
				CargaImputaciones cargaImputaciones = (CargaImputaciones)ios.readObject();
				result.add(cargaImputaciones);
			} catch (IOException | ClassNotFoundException e) {
				log.error("Error recuperando la carga del fichero '{}'", fichero.getAbsolutePath(), e);
			}
		}
		
		int cargasNumber = result.size();
		
		log.info("Ficheros con cargas: " + filesNumber + " - Cargas recuperadas: " + cargasNumber);
			
		return result;
	}

}
