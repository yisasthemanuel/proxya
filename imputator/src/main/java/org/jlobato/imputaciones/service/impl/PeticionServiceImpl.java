package org.jlobato.imputaciones.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.Peticion;
import org.jlobato.imputaciones.model.Proyecto;
import org.jlobato.imputaciones.model.RedMine;
import org.jlobato.imputaciones.service.PeticionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
/**
 * The Class PeticionServiceImpl.
 */
public class PeticionServiceImpl implements PeticionService {
	
	/** The default persona. */
	@Autowired
	@Qualifier("getProxyaDefaultPersona")
	Persona proxyaDefaultPersona;
	
	/** The RedMine driver. */
	@Autowired
	RedMineDriver redmineDriver;

	/**
	 * Crea peticion.
	 *
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @return the peticion
	 */
	@Override
	public Peticion creaPeticion(RedMine redmine, Proyecto proyecto) {
		return creaPeticion(redmine, proyecto, proxyaDefaultPersona);
	}

	/**
	 * Crea peticiones mensuales.
	 *
	 * @param anno the anno
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @return the list
	 */
	@Override
	public List<Peticion> creaPeticionesMensuales(int anno, RedMine redmine, Proyecto proyecto) {
		return creaPeticionesMensuales(anno, redmine, proyecto, proxyaDefaultPersona);
	}

	/**
	 * Crea peticiones mensuales.
	 *
	 * @param anno the anno
	 * @param mesInicio the mes inicio
	 * @param mesFin the mes fin
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @return the list
	 */
	@Override
	public List<Peticion> creaPeticionesMensuales(int anno, int mesInicio, int mesFin, RedMine redmine, Proyecto proyecto) {
		return creaPeticionesMensuales(anno, mesInicio, mesFin, redmine, proyecto, proxyaDefaultPersona);
	}

	/**
	 * Crea peticion.
	 *
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @param autor the autor
	 * @return the peticion
	 */
	@Override
	public Peticion creaPeticion(RedMine redmine, Proyecto proyecto, Persona autor) {
		return null;
		//return redmineDriver.creaPeticion(redmine.getUri(), proyecto.getIdentificador(), autor.getApiKey());
	}

	/**
	 * Crea peticiones mensuales.
	 *
	 * @param anno the anno
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @param autor the autor
	 * @return the list
	 */
	@Override
	public List<Peticion> creaPeticionesMensuales(int anno, RedMine redmine, Proyecto proyecto, Persona autor) {
		return creaPeticionesMensuales(anno, 1, 12, redmine, proyecto, autor);
	}

	/**
	 * Crea peticiones mensuales.
	 *
	 * @param anno the anno
	 * @param mesInicio the mes inicio
	 * @param mesFin the mes fin
	 * @param redmine the redmine
	 * @param proyecto the proyecto
	 * @param autor the autor
	 * @return the list
	 */
	@Override
	public List<Peticion> creaPeticionesMensuales(int anno, int mesInicio, int mesFin, RedMine redmine, Proyecto proyecto, Persona autor) {
		/* Obtenemos la lista de meses */
		List<Peticion> result = new ArrayList<Peticion>();
	

        for (int mes = mesInicio; mes <= mesFin; mes++) {
            // Construir la fecha del primer día del mes
            LocalDate primerDia = LocalDate.of(anno, mes, 1);

            // Obtener el último día del mes
            LocalDate ultimoDia = primerDia.withDayOfMonth(primerDia.lengthOfMonth());

            // Obtener el asunto para la petición del mes
            String asunto = "[Horas] " + String.format("%02d", mes) + "-" + anno;
            
            // Obtener la descripción para la petición del mes
            // Formatear las fechas como cadenas
            String primerDiaStr = primerDia.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String ultimoDiaStr = ultimoDia.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            String descripcion = "Petición para la imputación de Horas del equipo educamos entre el " + primerDiaStr + " y el " + ultimoDiaStr;

            Peticion peticion = redmineDriver.creaPeticion(
            		redmine.getUri(),
            		proyecto.getIdentificador(),
            		autor.getApiKey(),
            		asunto,
            		descripcion,
            		Date.from(primerDia.atStartOfDay(ZoneId.systemDefault()).toInstant()),
            		Date.from(ultimoDia.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            
            result.add(peticion);
            log.info("Petición mensual añadida: {}", peticion);
        }
		
		
		return result;
	}

}
