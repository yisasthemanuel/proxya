package org.jlobato.imputaciones.repository;

import java.util.List;

import org.jlobato.imputaciones.model.Persona;

public interface PersonaRepository {
	public List<Persona> getAllPersonas();	
	public Persona getPersona(String nickname);
	public void addPersona(Persona persona);
	public void addPersona(String id, String nickname, String apiKey, String nombre, String primerApellido, String segundoApellido, String nombreCompleto);
	public void updatePersona(String nickname, String newApiKey);
}
