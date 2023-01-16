package org.jlobato.imputaciones.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.impl.PersonaImpl;
import org.jlobato.imputaciones.repository.PersonaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PersonaRepositoryDummyIImpl implements PersonaRepository {
	private static final Map<String, Persona> PERSONAS = new HashMap<>();
	
	static {
		int id = 1;
		//Jesús Manuel Pérez Lobato
		PERSONAS.put("jmpl06", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("ed7838edbbe8c1cee88413349219ed6967c7a90e")
				.nickname("jmpl06")
				.nombre("Jesús Manuel")
				.primerApellido("Pérez")
				.segundoApellido("Lobato")
				.nombreCompleto("Jesús Manuel Pérez Lobato")
				.build()
		);
		//Marina Caballero Galnares
		PERSONAS.put("mmcg165", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("e280e5a8cb18357313a9d14108f33239ce1752cd")
				.nickname("mmcg165")
				.nombre("Marina")
				.primerApellido("Caballero")
				.segundoApellido("Galnares")
				.nombreCompleto("Marina Caballero Galnares")
				.build()
		);
		//Rocío Mancebo Gutiérrez
		PERSONAS.put("rrmg127", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("535e97451199baf550faf766b19bf4fb43ebd0d7")
				.nickname("rrmg127")
				.nombre("Rocío")
				.primerApellido("Mancebo")
				.segundoApellido("Gutiérrez")
				.nombreCompleto("Rocío Mancebo Gutiérrez")
				.build()
		);
		//Jesús Noguera Marco
		PERSONAS.put("jjnm37", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("9699353b2e060c35d8bf3591af623a1e9e93d20b")
				.nickname("jjnm37")
				.nombre("Jesús")
				.primerApellido("Noguera")
				.segundoApellido("Marco")
				.nombreCompleto("Jesús Noguera Marco")
				.build()
		);
		//Ismael Fernández Zambrano
		PERSONAS.put("iifz04", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("e509d8935932b177493048b87e4fca6c10d0fc2d")
				.nickname("iifz04")
				.nombre("Ismael")
				.primerApellido("Fernández")
				.segundoApellido("Zambrano")
				.nombreCompleto("Ismael Fernández Zambrano")
				.build()
		);
		//Juan Carlos Maldonado Terriza
		PERSONAS.put("jcmt01", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("02e24cc6673db1ce5f3e445c095a6a90df71db77")
				.nickname("jcmt01")
				.nombre("Juan Carlos")
				.primerApellido("Maldonado")
				.segundoApellido("Terriza")
				.nombreCompleto("Juan Carlos Maldonado Terriza")
				.build()
		);
		//Leticia Morales Jiménez
		PERSONAS.put("llmj12", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("f0b2802e2d37beede17af39b2f43e84967b77dba")
				.nickname("llmj12")
				.nombre("Leticia")
				.primerApellido("Morales")
				.segundoApellido("Jiménez")
				.nombreCompleto("Leticia Morales Jiménez")
				.build()
		);
		//Ángel David Chamizo Benítez
		PERSONAS.put("adcb01", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("482e383a119a8467e21defc47c79f5d500ffc2ed")
				.nickname("adcb01")
				.nombre("Ángel David")
				.primerApellido("Chamizo")
				.segundoApellido("Benítez")
				.nombreCompleto("Ángel David Chamizo Benítez")
				.build()
		);
		//Ana María Boza Leiva
		PERSONAS.put("ambl06", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("baf2ef6b1f24bc2abe1cb6441dd80e6518de8fd6")
				.nickname("ambl06")
				.nombre("Ana María")
				.primerApellido("Boza")
				.segundoApellido("Leiva")
				.nombreCompleto("Ana María Boza Leiva")
				.build()
		);
		//Inés Sánchez Camacho
		PERSONAS.put("imsc03", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("0f52ca2e888c590cfee4d7ad38cdacc5a6af74f3")
				.nickname("imsc03")
				.nombre("Inés")
				.primerApellido("Sánchez")
				.segundoApellido("Camacho")
				.nombreCompleto("Inés Sánchez Camacho")
				.build()
		);
		//Susana Pinzón Tirado
		PERSONAS.put("sspt10", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("2b0bdd4b9e122721223373490571b83d7a7b30a8")
				.nickname("sspt10")
				.nombre("Susana")
				.primerApellido("Pinzón")
				.segundoApellido("Tirado")
				.nombreCompleto("Susana Pinzón Tirado")
				.build()
		);
		//Jose Alfonso Riera Peixoto
		PERSONAS.put("jarp12", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("c80196331c805e5c3ba71783ec11ff6a7463235c")
				.nickname("jarp12")
				.nombre("Jose Alfonso")
				.primerApellido("Riera")
				.segundoApellido("Peixoto")
				.nombreCompleto("Jose Alfonso Riera Peixoto")
				.build()
		);
		//Miguel Angel Mercado Begara
		PERSONAS.put("mamb19", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("22020c39cc053262f252de4c5fd333f97bc16c31")
				.nickname("mamb19")
				.nombre("Miguel Ángel")
				.primerApellido("Mercado")
				.segundoApellido("Begara")
				.nombreCompleto("Miguel Ángel Mercado Begara")
				.build()
		);
		//Mario Sánchez Rodríguez
		PERSONAS.put("mmsr148", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("971e3824df3261e4b70d6f914abe6e44e212b49e")
				.nickname("mmsr148")
				.nombre("Mario")
				.primerApellido("Sánchez")
				.segundoApellido("Rodríguez")
				.nombreCompleto("Mario Sánchez Rodríguez")
				.build()
		);
		//Francisco Sánchez Delgado
		PERSONAS.put("ffsd15", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("5051a03edc4a5445db509ac00855762d12ccd557")
				.nickname("ffsd15")
				.nombre("Francisco")
				.primerApellido("Sánchez")
				.segundoApellido("Delgado")
				.nombreCompleto("Francisco Sánchez Delgado")
				.build()
		);
		//Marta María Méndez Martínez
		PERSONAS.put("mmmm497", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("5d0d4adc5b577725ed3d5e8c96d2924e1fab837d")
				.nickname("mmmm497")
				.nombre("Marta María")
				.primerApellido("Méndez")
				.segundoApellido("Martínez")
				.nombreCompleto("Marta María Méndez Martínez")
				.build()
		);
		//Borja del Cura Ibáñez
		PERSONAS.put("bbdc16", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("1138b2776f9f78a41b710a168dfb99d8675e7bb7")
				.nickname("bbdc16")
				.nombre("Borja")
				.primerApellido("del Cura")
				.segundoApellido("Ibáñez")
				.nombreCompleto("Borja del Cura Ibáñez")
				.build()
		);
		//Álvaro Catalán Sánchez
		PERSONAS.put("aacs137", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("fee05e62d60eeff355ddad745a9f8b1654376d9a")
				.nickname("aacs137")
				.nombre("Álvaro")
				.primerApellido("Catalán")
				.segundoApellido("Sánchez")
				.nombreCompleto("Álvaro Catalán Sánchez")
				.build()
		);
		//Rafael Hernández Gómez
		PERSONAS.put("rrhg20", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("e1710ce2f6ce940e64ed4c7407748f6863fceac0")
				.nickname("rrhg20")
				.nombre("Rafael")
				.primerApellido("Hernández")
				.segundoApellido("Gómez")
				.nombreCompleto("Rafael Hernández Gómez")
				.build()
		);
		//Raúl Micharet Arcos
		PERSONAS.put("rrma72", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("fa635119a82c59f01055f934dda06de0d411cdb6")
				.nickname("rrma72")
				.nombre("Raúl")
				.primerApellido("Micharet")
				.segundoApellido("Arcos")
				.nombreCompleto("Raúl Micharet Arcos")
				.build()
		);
		//Antonio Agustín Casado Sánchez
		PERSONAS.put("aacs122", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("a41226e597e5d31f87b083719dcb857be49e2c57")
				.nickname("aacs122")
				.nombre("Antonio Agustín")
				.primerApellido("Casado")
				.segundoApellido("Sánchez")
				.nombreCompleto("Antonio Agustín Casado Sánchez")
				.build()
		);
		//Juan Antonio Lucena Aguilar
		PERSONAS.put("jala09", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("55d32c72ae392970c091a417ed01b5eed48c849c")
				.nickname("jala09")
				.nombre("Juan Antonio")
				.primerApellido("Lucena")
				.segundoApellido("Aguilar")
				.nombreCompleto("Juan Antonio Lucena Aguilar")
				.build()
		);
		//Jorge Madejón Morán
		PERSONAS.put("jjmm354", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("917631dc269f55cf053bba7f0bd0b2927c667f46")
				.nickname("jjmm354")
				.nombre("Jorge")
				.primerApellido("Madejón")
				.segundoApellido("Morán")
				.nombreCompleto("Jorge Madejón Morán")
				.build()
		);
		//Juan Manuel Márquez Rodríguez
		PERSONAS.put("jmmr25", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("7fcf7e2b166b072e75c6d6a6916e6d7ba1301f1c")
				.nickname("jmmr25")
				.nombre("Juan Manuel")
				.primerApellido("Márquez")
				.segundoApellido("Rodríguez")
				.nombreCompleto("Juan Manuel Márquez Rodríguez")
				.build()
		);
		//Victoria Fernández Rojo
		PERSONAS.put("vvfr10", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("799bf8a2934424cca33235f87974a1f73d12d245")
				.nickname("vvfr10")
				.nombre("Victoria")
				.primerApellido("Fernández")
				.segundoApellido("Rojo")
				.nombreCompleto("Victoria Fernández Rojo")
				.build()
		);
		//Juan Antonio Resurrección Galán
		PERSONAS.put("jarg17", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("1153acddc4dc619ad652005912814cc5700738e8")
				.nickname("jarg17")
				.nombre("Juan Antonio")
				.primerApellido("Resurrección")
				.segundoApellido("Galán")
				.nombreCompleto("Juan Antonio Resurrección Galán")
				.build()
		);
		//Antonio Espionsa Velasco
		PERSONAS.put("aaev11", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("fa07acb5d6c132a6dae354955e01e8ec7e05f723")
				.nickname("aaev11")
				.nombre("Antonio")
				.primerApellido("Espinosa")
				.segundoApellido("Velasco")
				.nombreCompleto("Antonio Espinosa Velasco")
				.build()
		);
		//Jose Luis López Martínez
		PERSONAS.put("jllm11", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("683f81f30ba9ee36e5a47dbaf8dfae70e31244d6")
				.nickname("jllm11")
				.nombre("Jose Luis")
				.primerApellido("López")
				.segundoApellido("Martínez")
				.nombreCompleto("Jose Luis López Martínez")
				.build()
		);
		//Jorge Vicente Gabriel
		PERSONAS.put("jjvg66", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("ab0bc5c4af390dc6e65634c0a1dd5da36faf4616")
				.nickname("jjvg66")
				.nombre("Jorge")
				.primerApellido("Vicente")
				.segundoApellido("Gabriel")
				.nombreCompleto("Jorge Vicente Gabriel")
				.build()
		);
		//Luis Fernando Cepeda Muñoz
		PERSONAS.put("lfcm02", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("3bfc08dc1f921ce10a8db74e97033383c8283d04")
				.nickname("lfcm02")
				.nombre("Luis Fernando")
				.primerApellido("Cepeda")
				.segundoApellido("Muñoz")
				.nombreCompleto("Luis Fernando Cepeda Muñoz")
				.build()
		);
		//Paula Lema Camean
		PERSONAS.put("pplc40", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("6e5867108977d0bfaf1a9620377e8585b2b50e22")
				.nickname("pplc40")
				.nombre("Paula")
				.primerApellido("Lema")
				.segundoApellido("Camean")
				.nombreCompleto("Paula Lema Camean")
				.build()
		);
		//Manuel Muñoz Castro
		PERSONAS.put("mmmc310", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("521a24db79f8ed5061ef8c6ee24fea937f5436a4")
				.nickname("mmmc310")
				.nombre("Manuel")
				.primerApellido("Muñoz")
				.segundoApellido("Castro")
				.nombreCompleto("Manuel Muñoz Castro")
				.build()
		);
		//Inmaculada Ponce Gonzalez
		PERSONAS.put("iipg51", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("87cb282f3ab75c0fb3f9fe33c0715d6309f8a81a")
				.nickname("iipg51")
				.nombre("Inmaculada")
				.primerApellido("Ponce")
				.segundoApellido("Gonzalez")
				.nombreCompleto("Inmaculada Ponce Gonzalez")
				.build()
		);
		//Jose Ramón Rubio Romera
		PERSONAS.put("jrrr07", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("90d2e518f9e698382ea5003f46b914bc55490ca2")
				.nickname("jrrr07")
				.nombre("Jose Ramón")
				.primerApellido("Rubio")
				.segundoApellido("Romera")
				.nombreCompleto("Jose Ramón Rubio Romera")
				.build()
		);
		//María Teresa Lahoz López
		PERSONAS.put("mtll06", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("1a5d97a3c09e6608ec9dcfd5ad920bd721110080")
				.nickname("mtll06")
				.nombre("Jose Ramón")
				.primerApellido("Rubio")
				.segundoApellido("Romera")
				.nombreCompleto("Jose Ramón Rubio Romera")
				.build()
		);
		//Pedro Ramos Medero
		PERSONAS.put("pprm49", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("4741f21fe8c4a63ceeb893d994777b0ce5573f03")
				.nickname("pprm49")
				.nombre("Pedro")
				.primerApellido("Ramos")
				.segundoApellido("Medero")
				.nombreCompleto("Pedro Ramos Medero")
				.build()
		);
		//Pablo Moreno Velamazán
		PERSONAS.put("ppmv32", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("decd166dce1919f843e3a8a60ba5ab59e3b7f094")
				.nickname("ppmv32")
				.nombre("Pablo")
				.primerApellido("Moreno")
				.segundoApellido("Velamazán")
				.nombreCompleto("Pablo Moreno Velamazán")
				.build()
		);
		//Jose Luis Guerra Díaz
		PERSONAS.put("jlgd11", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("decd166dce1919f843e3a8a60ba5ab59e3b7f094")
				.nickname("jlgd11")
				.nombre("Jose Luis")
				.primerApellido("Guerra")
				.segundoApellido("Díaz")
				.nombreCompleto("Jose Luis Guerra Díaz")
				.build()
		);
		//Alejandro Díaz Guerrero
		PERSONAS.put("aadg55", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("c13a54161aeaa8783330172816b2c00232ec3877")
				.nickname("aadg55")
				.nombre("Alejandro")
				.primerApellido("Díaz")
				.segundoApellido("Guerrero")
				.nombreCompleto("Alejandro Díaz Guerrero")
				.build()
		);
		//Jose Martín López Blanco
		PERSONAS.put("jmlb04", PersonaImpl.builder()
				.id(String.valueOf(id++))
				.apiKey("10e628247efae170ae6015e179587b49085f69ce")
				.nickname("jmlb04")
				.nombre("Jose Martín")
				.primerApellido("López")
				.segundoApellido("Blanco")
				.nombreCompleto("Jose Martín López Blanco")
				.build()
		);
		
		
		//A partir de aquí son usuarios para hacer pruebas
		//Admin (for testing purposes)
		PERSONAS.put("admin009$", PersonaImpl.builder()
				.id("900")
				.apiKey("e5c259b7f150b64b942be48b0cae8ffc3a0ec3f7")
				.nickname("admin009$")
				.nombre("Redmine")
				.primerApellido("Admin")
				.segundoApellido("Admin")
				.nombreCompleto("Redmine Admin Admin")
				.build()
		);
		
		//jlobato(for testing purposes)
		PERSONAS.put("jlobato", PersonaImpl.builder()
				.id("901")
				.apiKey("5163fe4279c2aa388d63b4e215e12325ec9c3f79")
				.nickname("jlobato")
				.nombre("Jesusito Manuel")
				.primerApellido("Pérez")
				.segundoApellido("Lobato")
				.nombreCompleto("Jesusito Manuel Pérez Lobato")
				.build()
		);
		
	}
	

	@Override
	public List<Persona> getAllPersonas() {
		return new ArrayList<>(PERSONAS.values());
	}

	@Override
	public Persona getPersona(String nickname) {
		return PERSONAS.get(nickname);
	}

	@Override
	public void addPersona(Persona persona) {
		PERSONAS.put(persona.getNickname(), persona);
	}

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

	@Override
	public void updatePersona(String nickname, String newApiKey) {
		Persona persona = this.getPersona(nickname);
		if (persona != null) {
			PERSONAS.remove(nickname);
			Persona personaNew = PersonaImpl.builder()
					.id(persona.getId())
					.nickname(persona.getNickname())
					.apiKey(newApiKey)
					.nombre(persona.getNombre())
					.primerApellido(persona.getPrimerApellido())
					.segundoApellido(persona.getSegundoApellido())
					.nombreCompleto(persona.getNombreCompleto())
					.build();
			PERSONAS.put(nickname, personaNew);
		}
	}

}
