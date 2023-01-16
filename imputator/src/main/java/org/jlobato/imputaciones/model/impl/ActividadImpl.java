package org.jlobato.imputaciones.model.impl;

import org.jlobato.imputaciones.model.Actividad;

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
public class ActividadImpl implements Actividad {
	
	private int id;
	private String descripcion;
}
