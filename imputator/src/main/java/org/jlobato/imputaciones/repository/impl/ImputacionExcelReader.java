package org.jlobato.imputaciones.repository.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jlobato.imputaciones.model.Imputacion;
import org.jlobato.imputaciones.model.impl.ImputacionImpl;
import org.jlobato.imputaciones.repository.ImputacionReader;

import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/**
 * The Class ImputacionSETAExcelReader.
 *
 * @author Jesús Manuel Pérez
 */

/** The Constant log. */

/** The Constant log. */

/** The Constant log. */
@Slf4j
public class ImputacionExcelReader implements ImputacionReader {
	
	/** The path. */
	private String path;

	/**
	 * Gets the imputaciones.
	 *
	 * @return the imputaciones
	 */
	@Override
	public List<Imputacion> getImputaciones() {
		List<Imputacion> result = new ArrayList<>();
		try (FileInputStream excelFile = new FileInputStream(getPath())) {
			result = getImputaciones(excelFile);
		} catch (FileNotFoundException e) {
			log.error("Fichero no encontrado: {}", path, e);
		} catch (IOException e) {
			log.error("Error al leer el fichero: {}", path, e);
		}
		return result;
	}

	/**
	 * Gets the imputaciones.
	 *
	 * @param excelStream the excel stream
	 * @return the imputaciones
	 */
	@SuppressWarnings("deprecation")
	public List<Imputacion> getImputaciones(InputStream excelStream) {
		List<Imputacion> result = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(excelStream)) {
			Sheet sheet = workbook.getSheet("API_SETA");
			Iterator<Row> iterator = sheet.iterator();
			int fila = 0;

			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				if (fila == 0) {
					// Nos saltamos la cabecera
					currentRow = iterator.next();
					fila++;
				}

				// Creamos la imputación
				ImputacionImpl imputacion = ImputacionImpl.builder().build();

				// Iteramos por las columnas
				Iterator<Cell> iteratorRow = currentRow.iterator();
				//TODO Mejorar y utilizar el nombre de las columnas, no el número de la columna
				while (iteratorRow.hasNext()) {
					Cell currentCell = iteratorRow.next();
					String value = "";
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						value = currentCell.getStringCellValue();
					} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						value = Double.toString(currentCell.getNumericCellValue());
					} else if (currentCell.getCellTypeEnum() == CellType.FORMULA) {
						value = Double.toString(currentCell.getNumericCellValue());
					}
					switch (currentCell.getColumnIndex()) {
					case 0: // Id de la petición
						imputacion.setId(Integer.parseInt(value.substring(0, value.indexOf('.'))));
						break;
					case 1: // Mes de la imputación
						imputacion.setMes(Integer.parseInt(value.substring(0, value.indexOf('.'))));
						break;
					case 2:
						imputacion.setAnho(Integer.parseInt(value.substring(0, value.indexOf('.'))));
						break;
					case 3:
						imputacion.setAFSPermanente(value);
						break;
					case 4:
						imputacion.setAPPermanente(value);
						break;
					case 5:
						imputacion.setPRPermanente(value);
						break;
					case 6:
						imputacion.setAFSBolsa(value);
						break;
					case 7:
						imputacion.setAPBolsa(value);
						break;
					case 8:
						imputacion.setPRBolsa(value);
						break;
					default:
						break;
					}
				}
				if (imputacion.getId() > 0) {
					fila++;
					result.add(imputacion);
					log.debug("Imputación añadida: " + imputacion);
				}
			}
		} catch (Exception e) {
			log.error("Error obteniendo las imputaciones del excel: {}", e.getMessage(), e);
		}
		
		return result;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
