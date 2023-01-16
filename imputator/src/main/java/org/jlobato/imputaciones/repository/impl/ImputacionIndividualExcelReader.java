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
import org.jlobato.imputaciones.model.Actividad;
import org.jlobato.imputaciones.model.ImputacionIndividual;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.impl.ImputacionIndividualImpl;
import org.jlobato.imputaciones.repository.ActividadRepository;
import org.jlobato.imputaciones.repository.ImputacionIndividualReader;
import org.jlobato.imputaciones.repository.PersonaRepository;
import org.jlobato.imputaciones.util.ParseadorHorasUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ImputacionIndividualExcelReader implements ImputacionIndividualReader {
	
	@Autowired
	private Persona personaPorDefecto;
	
	@Autowired
	private SimpleDateFormat formateadorFechaImputaciones;
	
	@Autowired
	private ActividadRepository actividadRepos;

	@Autowired
	private PersonaRepository personaRepos;
	
	public static final String IMPUTACIONES_DEFAULT_SHEET_NAME = "IMPUTACIONES";
	
	/** The path. */
	private String path;

	/**
	 * Gets the imputaciones.
	 *
	 * @return the imputaciones
	 */
	@Override
	public List<ImputacionIndividual> getImputaciones() {
		List<ImputacionIndividual> result = new ArrayList<>();
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
	public List<ImputacionIndividual> getImputaciones(InputStream excelStream) {
		
		//TODO Asegurar que cuando hay un error se cierra el imput stream -> puede que valga eon el try with resources
		List<ImputacionIndividual> result = new ArrayList<>();
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
				ImputacionIndividualImpl.ImputacionIndividualImplBuilder imputacionIndividualBuilder = ImputacionIndividualImpl.builder();

				// Iteramos por las columnas
				Iterator<Cell> iteratorRow = currentRow.iterator();
				//TODO Mejorar y utilizar el nombre de las columnas, no el número de la columna
				while (iteratorRow.hasNext()) {
					Cell currentCell = iteratorRow.next();
					String value = getCellValue(currentCell);
					int cellColumn = currentCell.getColumnIndex();
					switch (cellColumn) {
//					switch (currentCell.getColumnIndex()) {
						case 0: // Id de la petición
							imputacionIndividualBuilder.id(Integer.parseInt(value.substring(0, value.indexOf('.'))));
							break;
						case 1: // Persona a la que corresponde la imputacion
							Persona persona = personaRepos.getPersona(value);
							if (persona == null) {
								persona = personaPorDefecto;
							}
							imputacionIndividualBuilder.persona(persona); //TODO -> Crear repostiorio de personas y hacer la búsqueda por el nick
							break;
						case 2: // Fecha de la imputación en formato DD/MM/YYYY
							imputacionIndividualBuilder.fecha(formateadorFechaImputaciones.parse(value));
							break;
						case 3: // Horas correspondientes a la imputación
							imputacionIndividualBuilder.horas(ParseadorHorasUtil.parseaHoras(value));
							break;
						case 4: // Actividad. Sale de una lista
							String activity = value;
							if (value.indexOf('.') > 0) {
								activity = value.substring(0, value.indexOf('.'));
							}
							Actividad actividad = actividadRepos.getActividad(activity);
							imputacionIndividualBuilder.actividad(actividad.getId()); //TODO -> Tener un servicio de actividades
							break;
						case 5:
							imputacionIndividualBuilder.comentario(value);
							break;
						default:
							break;
					}
				}
				ImputacionIndividual imputacion = imputacionIndividualBuilder.build();
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
		    switch (currentCell.getCachedFormulaResultType()) {
		        case Cell.CELL_TYPE_BOOLEAN:
		        	value = Boolean.toString(currentCell.getBooleanCellValue());
		            break;
		        case Cell.CELL_TYPE_NUMERIC:
					double dv = currentCell.getNumericCellValue();
					//Por si viene una fecha
					if (DateUtil.isCellDateFormatted(currentCell)) {
						Date date = DateUtil.getJavaDate(dv);
						value = formateadorFechaImputaciones.format(date);
					}
					else {
						value = Double.toString(dv);
					}
		            break;
		        case Cell.CELL_TYPE_STRING:
		        	value = currentCell.getRichStringCellValue().toString();
		            break;
		    }
		}
			
			//value = Double.toString(currentCell.getNumericCellValue());
			//value = currentCell.getStringCellValue();
			//value = formatter.formatCellValue(currentCell);
		
		
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
