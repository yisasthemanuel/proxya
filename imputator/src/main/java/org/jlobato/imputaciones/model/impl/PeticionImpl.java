package org.jlobato.imputaciones.model.impl;

import java.util.Date;

import org.jlobato.imputaciones.model.Peticion;

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
public class PeticionImpl implements Peticion {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2474440151867048821L;

	private int id;
	public String asunto;
	public String descripcion;
	public Date fechaInicio;
	public Date fechaFin;
}
