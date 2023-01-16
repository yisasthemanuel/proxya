package org.jlobato.imputaciones.model.impl;

import org.jlobato.imputaciones.model.Estimacion;
import org.jlobato.imputaciones.model.Persona;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class EstimacionImpl implements Estimacion {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5739944856207223979L;
	
	/**
	 * Id de la petición que se estima 
	 */
	private int id;
	
	/**
	 * Persona que realiza la imputación. La persona que figurará en Gesproy como autor de la actualización de las horas estimadas
	 */
	private Persona persona;
	
	/**
	 * Valor en horas de la estimación
	 */
	private float horas;
}