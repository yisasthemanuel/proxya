package org.jlobato.imputaciones.repository.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.impl.PersonaImpl;
import org.jlobato.imputaciones.repository.PersonaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Class PersonaRepositoryDummyIImpl.
 */
@Repository
public class PersonaRepositoryDummyImpl implements PersonaRepository {
	
	/** The Constant PERSONAS. */
	private static Map<String, Persona> personas = new HashMap<>();
	
	static {
		//Recuperamos
		loadRepository();
	}
	
	/**
	 * Save repository.
	 */
	private static void saveRepository() {
		String classLocation = PersonaRepositoryDummyImpl.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		File newFile = new File(new File(classLocation).getParentFile(), "personas.ser");
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(newFile))) {
			oos.writeObject(personas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load repository.
	 */
	@SuppressWarnings("unchecked")
	private static void loadRepository() {
		try (ObjectInputStream ois = new ObjectInputStream(PersonaRepositoryDummyImpl.class.getResourceAsStream("/personas.ser"))) {
			personas = (Map<String, Persona>)ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Gets the all personas.
	 *
	 * @return the all personas
	 */
	@Override
	public List<Persona> getAllPersonas() {
		return new ArrayList<>(personas.values());
	}

	/**
	 * Gets the persona.
	 *
	 * @param nickname the nickname
	 * @return the persona
	 */
	@Override
	public Persona getPersona(String nickname) {
		String idPersona = nickname;
		if (nickname != null) {
			idPersona = nickname.trim();
		}
		return personas.get(idPersona);
	}

	/**
	 * Adds the persona.
	 *
	 * @param persona the persona
	 */
	@Override
	public void addPersona(Persona persona) {
		personas.put(persona.getNickname(), persona);
		saveRepository();
	}

	/**
	 * Adds the persona.
	 *
	 * @param id the id
	 * @param nickname the nickname
	 * @param apiKey the api key
	 * @param nombre the nombre
	 * @param primerApellido the primer apellido
	 * @param segundoApellido the segundo apellido
	 * @param nombreCompleto the nombre completo
	 */
	@Override
	public void addPersona(String id, String nickname, String apiKey, String nombre, String primerApellido, String segundoApellido, String nombreCompleto) {
		this.addPersona(PersonaImpl.builder()
			.id(id)
			.nickname(nickname)
			.apiKey(apiKey)
			.nombre(nombre)
			.primerApellido(primerApellido)
			.segundoApellido(segundoApellido)
			.nombreCompleto(nombreCompleto)
			.build()
		);
	}

	/**
	 * Update persona.
	 *
	 * @param nickname the nickname
	 * @param newApiKey the new api key
	 */
	@Override
	public void updatePersona(String nickname, String newApiKey) {
		Persona persona = this.getPersona(nickname);
		if (persona != null) {
			this.updatePersona(nickname, persona.getId(), newApiKey, persona.getNombre(), persona.getPrimerApellido(), persona.getSegundoApellido(), persona.getNombreCompleto());
		}		
	}

	/**
	 * Update persona.
	 *
	 * @param nickname the nickname
	 * @param newApiKey the new api key
	 * @param nombre the nombre
	 * @param primerApellido the primer apellido
	 * @param segundoApellido the segundo apellido
	 * @param nombreCompleto the nombre completo
	 */
	@Override
	public void updatePersona(String nickname, String newApiKey, String nombre, String primerApellido, String segundoApellido, String nombreCompleto) {
		Persona persona = this.getPersona(nickname);
		if (persona != null) {
			this.updatePersona(nickname, persona.getId(), newApiKey, nombre, primerApellido, segundoApellido, nombreCompleto);
		}
	}
	
	/**
	 * Update persona.
	 *
	 * @param nickname the nickname
	 * @param id the id
	 * @param newApiKey the new api key
	 * @param nombre the nombre
	 * @param primerApellido the primer apellido
	 * @param segundoApellido the segundo apellido
	 * @param nombreCompleto the nombre completo
	 */
	private void updatePersona(String nickname, String id, String newApiKey, String nombre, String primerApellido, String segundoApellido, String nombreCompleto) {
		personas.remove(nickname);
		Persona personaNew = PersonaImpl.builder()
				.id(id)
				.nickname(nickname)
				.apiKey(newApiKey)
				.nombre(nombre)
				.primerApellido(primerApellido)
				.segundoApellido(segundoApellido)
				.nombreCompleto(nombreCompleto)
				.build();
		personas.put(nickname, personaNew);
		saveRepository();
	} 

}
