package org.jlobato.imputaciones.repository.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jlobato.imputaciones.model.Estimacion;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.impl.EstimacionImpl;
import org.jlobato.imputaciones.repository.EstimacionReader;
import org.jlobato.imputaciones.repository.PersonaRepository;
import org.jlobato.imputaciones.util.ParseadorHorasUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EstimacionExcelReader implements EstimacionReader {

	@Autowired
	@Qualifier("getDefaultPersona")
	private Persona defaultPersona;
	
	@Autowired
	private SimpleDateFormat formateadorFechaImputaciones;
	
	@Autowired
	private PersonaRepository personaRepos;
	
	public static final String IMPUTACIONES_DEFAULT_SHEET_NAME = "ESTIMACIONES";
	
	/** The path. */
	private String path;

	@Override
	public List<Estimacion> getEstimaciones() {
		List<Estimacion> result = new ArrayList<>();
		try (FileInputStream excelFile = new FileInputStream(getPath())) {
			result = getEstimaciones(excelFile);
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
	public List<Estimacion> getEstimaciones(InputStream excelStream) {
		
		// TODO Asegurar que cuando hay un error se cierra el imput stream -> puede que valga eon el try with resources
		List<Estimacion> result = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(excelStream)) {
			Sheet sheet = workbook.getSheet(IMPUTACIONES_DEFAULT_SHEET_NAME);
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
				EstimacionImpl.EstimacionImplBuilder estimacionBuilder = EstimacionImpl.builder();

				// Iteramos por las columnas
				Iterator<Cell> iteratorRow = currentRow.iterator();
				// TODO Mejorar y utilizar el nombre de las columnas, no el número de la columna
				while (iteratorRow.hasNext()) {
					Cell currentCell = iteratorRow.next();
					String value = getCellValue(currentCell);
					switch (currentCell.getColumnIndex()) {
						case 0: // Id de la petición
							estimacionBuilder.id(Integer.parseInt(value.substring(0, value.indexOf('.'))));
							break;
						case 1: // Persona a la que corresponde la imputacion
							Persona persona = personaRepos.getPersona(value);
							if (persona == null) {
								persona = defaultPersona;
							}
							estimacionBuilder.persona(persona);
							break;
						case 2: // Horas correspondientes a la estimación
							estimacionBuilder.horas(ParseadorHorasUtil.parseaHoras(value));
							break;
						default:
							break;
					}
				}
				Estimacion estimacion = estimacionBuilder.build();
				if (estimacion.getId() > 0) {
					fila++;
					result.add(estimacion);
					log.debug("Estimación añadida: " + estimacion);
				}
			}
		} catch (Exception e) {
			log.error("Error obteniendo las estimaciones del excel: {}", e.getMessage(), e);
		}
		
		return result;
	}

	@SuppressWarnings("deprecation")
	private String getCellValue(Cell currentCell) {
		String value = "";
		if (currentCell.getCellTypeEnum() == CellType.STRING) {
			value = currentCell.getStringCellValue();
		} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
			double dv = currentCell.getNumericCellValue();
			//Por si viene una fecha
			if (DateUtil.isCellDateFormatted(currentCell)) {
				Date date = DateUtil.getJavaDate(dv);
				value = formateadorFechaImputaciones.format(date);
			}
			else {
				value = Double.toString(dv);
			}
		} else if (currentCell.getCellTypeEnum() == CellType.FORMULA) {
			value = currentCell.getStringCellValue();
		}
		
		
		return value;
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
