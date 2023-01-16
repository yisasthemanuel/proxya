package org.jlobato.imputaciones.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jlobato.imputaciones.model.Actividad;
import org.jlobato.imputaciones.model.impl.ActividadImpl;
import org.jlobato.imputaciones.repository.ActividadRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ActividadRepositoryDummyImpl implements ActividadRepository {
	private static final Map<Integer, Actividad> ACTIVIDADES = new HashMap<>();
	
//	<option value="31">Actas e informes</option>
//	<option value="16">Analisis</option>
//	<option value="34">Coordinación y planificación</option>
//	<option value="9">Desarrollo</option>
//	<option value="15">Definición de propuestas</option>
//	<option value="13">Deteccion del error</option>
//	<option value="8">Diseño</option>
//	<option value="29">Formación</option>
//	<option value="10">Documentación</option>
//	<option value="14">Estudios</option>
//	<option value="12">Pruebas</option>
//	<option value="30">Reunión</option>
//	<option value="17">Tratamiento directo de datos</option>
	
	static {
		//Actas e informes
		ACTIVIDADES.put(31, ActividadImpl.builder()
				.id(31)
				.descripcion("Actas e informes")
				.build()
		);
		
		//Analisis - Sin tilde en la a...		
		ACTIVIDADES.put(16, ActividadImpl.builder()
				.id(16)
				.descripcion("Analisis")
				.build()
		);
		
		//Coordinación y planificación
		ACTIVIDADES.put(34, ActividadImpl.builder()
				.id(34)
				.descripcion("Coordinación y planificación")
				.build()
		);
		
		//Desarrollo
		ACTIVIDADES.put(9, ActividadImpl.builder()
				.id(9)
				.descripcion("Desarrollo")
				.build()
		);
		
		//Definición de propuestas
		ACTIVIDADES.put(15, ActividadImpl.builder()
				.id(15)
				.descripcion("Definición de propuestas")
				.build()
		);
		
		//Deteccion del error
		ACTIVIDADES.put(13, ActividadImpl.builder()
				.id(13)
				.descripcion("Deteccion del error")
				.build()
		);
		
		//Diseño
		ACTIVIDADES.put(8, ActividadImpl.builder()
				.id(8)
				.descripcion("Diseño")
				.build()
		);

		//Formación
		ACTIVIDADES.put(29, ActividadImpl.builder()
				.id(29)
				.descripcion("Formación")
				.build()
		);

		//Documentación
		ACTIVIDADES.put(10, ActividadImpl.builder()
				.id(10)
				.descripcion("Documentación")
				.build()
		);

		//Estudios
		ACTIVIDADES.put(14, ActividadImpl.builder()
				.id(14)
				.descripcion("Estudios")
				.build()
		);

		//Pruebas
		ACTIVIDADES.put(12, ActividadImpl.builder()
				.id(12)
				.descripcion("Pruebas")
				.build()
		);

		//Reunión
		ACTIVIDADES.put(30, ActividadImpl.builder()
				.id(30)
				.descripcion("Reunión")
				.build()
		);

		//Tratamiento directo de datos
		ACTIVIDADES.put(17, ActividadImpl.builder()
				.id(17)
				.descripcion("Tratamiento directo de datos")
				.build()
		);
	}
	

	@Override
	public List<Actividad> getAllActividades() {
		return new ArrayList<>(ACTIVIDADES.values());
	}

	@Override
	public Actividad getActividad(String codigo) {
		Actividad result = null;
		int id = -1;
		try {
			id = Integer.parseInt(codigo);
		} catch (Exception e) {
			result = getActividadPorDescripcion(codigo);
		}
		// Si no hemos encontrado por descripcion o tenemos un código, directamente buscamos por código
		if (result == null) {
			result = ACTIVIDADES.get(id);
		}
		
		return result;
	}

	private Actividad getActividadPorDescripcion(String codigo) {
		Actividad result = null;
		
		Iterator<Actividad> iterador = ACTIVIDADES.values().iterator();
		boolean encontrado = false;
		while (iterador.hasNext() && !encontrado) {
			Actividad current = iterador.next();
			if (current.getDescripcion().equals(codigo)) {
				encontrado = true;
				result = current;
			}
		}
		return result;
	}

	@Override
	public void addActividad(Actividad actividad) {
		ACTIVIDADES.put(actividad.getId(), actividad);
	}

}
