package org.jlobato.imputaciones.model.impl;

import java.util.Date;

import org.jlobato.imputaciones.model.ImputacionIndividual;
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
public class ImputacionIndividualImpl implements ImputacionIndividual {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4834090827271156618L;
	private int id;
	private Date fecha;
	private int actividad;
	private float horas;
	private String comentario;
	private Persona persona;
}
