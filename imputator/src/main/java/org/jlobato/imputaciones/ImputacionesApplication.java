package org.jlobato.imputaciones;

import java.util.Iterator;
import java.util.Map;

import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.Proyecto;
import org.jlobato.imputaciones.model.impl.PersonaImpl;
import org.jlobato.imputaciones.model.impl.ProyectoImpl;
import org.jlobato.imputaciones.repository.PersonaRepository;
import org.jlobato.imputaciones.service.EstimacionService;
import org.jlobato.imputaciones.service.ImputacionService;
import org.jlobato.imputaciones.service.PeticionService;
import org.jlobato.imputaciones.service.ProyectoService;
import org.jlobato.imputaciones.service.RedMineService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase ImputacionesApplication.
 */
@SpringBootApplication
@Slf4j
public class ImputacionesApplication {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ImputacionesApplication.class, args);
	}
	
	/**
	 * Test get imputaciones.
	 *
	 * @param imputacionService the imputacion service
	 * @param targetRedMineService the redmine service
	 * @param projectService the project service
	 * @param repository the repository
	 * @return the command line runner
	 */
	@Bean
	CommandLineRunner testGetImputaciones(ImputacionService imputacionService, RedMineService targetRedMineService, ProyectoService projectService, PersonaRepository repository, PeticionService peticionService, EstimacionService estimacionService) {
		return args -> {
		};
	}
	
	/**
	 * Inicialización del servicio SETA.
	 *
	 * @param imputacionService Servicio SETA que se va a iniciar
	 * @param config the config
	 * @return the command line runner
	 */
	@Bean
	CommandLineRunner initSetaService(ImputacionService imputacionService, Map<String, Object> redMineProperties) {
		return args -> imputacionService.init();
	}
	
	/**
	 * Crea peticiones mensuales.
	 *
	 * @param peticionService Servicio para crear las peticiones mensuales
	 * @param redmineService Servicio para el Redmine en donde se crearán las peticiones
	 * @param proyectoService Servicio para obtener el proyecto en donde se crearán las peticiones
	 * @return 
	 */
	@Bean
	CommandLineRunner addPeticionesMensuales(PeticionService peticionService, RedMineService redmineService, ProyectoService proyectoService) {
		return args -> {
//			List<Peticion> peticiones = peticionService.creaPeticionesMensuales(2024, 9, 12, redmineService.getRedMine(4), proyectoService.getProyectoByIdentificador("jccm-educamosclm"));
//			peticiones.addAll(peticionService.creaPeticionesMensuales(2025, 1, 8, redmineService.getRedMine(4), proyectoService.getProyectoByIdentificador("jccm-educamosclm")));
//			Iterator<Peticion> iteraPeticiones = peticiones.iterator();
//			log.info("Peticiones creadas: {}", peticiones);
//			while (iteraPeticiones.hasNext()) {
//				log.info("Petición: {}", iteraPeticiones.next());
//			}
		};
	}
	
	/**
	 * Lista las personas que están en el repositorio. Opcionalmente incluye más personas
	 *
	 * @param repository El repositorio de personas
	 * @return 
	 */
	@Bean
	CommandLineRunner addPersona(PersonaRepository repository) {
		return args -> {
			
//			Persona persona = PersonaImpl.builder()
//			.id("200200")
//			.apiKey("2df0ba4715e4f32041dd7cd3358e4a7cf6209b71")
//			.nickname("fujitsu_jabenitez@ayuncordoba.org")
//			.nombre("Jose Antonio")
//			.primerApellido("Benítez")
//			.segundoApellido("Montero")
//			.nombreCompleto("Jose Antonio Benítez Montero")
//			.build();
//			repository.addPersona(persona);
//			
//			persona = PersonaImpl.builder()
//					.id("200201")
//					.apiKey("12bbc5bc43211e2566a83868fc3ecca4900975c1")
//					.nickname("proxya_ifernandez@ayuncordoba.org")
//					.nombre("Ismael")
//					.primerApellido("Fernández")
//					.segundoApellido("Zambrano")
//					.nombreCompleto("Ismael Fernández Zambrano")
//					.build();
//			repository.addPersona(persona);
//			
//			persona = PersonaImpl.builder()
//					.id("200202")
//					.apiKey("fceb749f2a53a294d7e2e4ec1e9e8c313a267ac6")
//					.nickname("proxya_jmperez@ayuncordoba.org")
//					.nombre("Jesús Manuel")
//					.primerApellido("Pérez")
//					.segundoApellido("Lobato")
//					.nombreCompleto("Jesús Manuel Pérez Lobato")
//					.build();
//			repository.addPersona(persona);
//
//			persona = PersonaImpl.builder()
//					.id("10052")
//					.apiKey("59872ca43e34b151002ca0d84d1c783455319afd")
//					.nickname("hhem01")
//					.nombre("Habiba")
//					.primerApellido("El Mamouni")
//					.segundoApellido("Maarouf")
//					.nombreCompleto("Habiba El Mamouni Maarouf")
//					.build();
//			repository.addPersona(persona);
//
//			persona = PersonaImpl.builder()
//					.id("10053")
//					.apiKey("1fb38953988be4df6f816a10a1d9e77dbe48b610")
//					.nickname("ffdd20")
//					.nombre("Fernando")
//					.primerApellido("Díaz del Río")
//					.segundoApellido("Casal")
//					.nombreCompleto("Fernando Días del Río Casal")
//					.build();
//			repository.addPersona(persona);
			
			Iterator<Persona> personas = repository.getAllPersonas().iterator();
			
			while (personas.hasNext()) {
				log.info("PERSONA[{}]", personas.next());
			}
			
		};
	}

    /**
     * Devuelve la persona por defecto que crea la peticiones cualquier Redmine menos el de Proxya.
     *
     * @return Objeto persona para todos los redmines
     */
    @Bean
    Persona getDefaultPersona() {
		Persona persona = PersonaImpl.builder()
				.id("23")
				.apiKey("ed7838edbbe8c1cee88413349219ed6967c7a90e")
				.nickname("jmpl06")
				.nombre("Jesús Manuel")
				.primerApellido("Pérez")
				.segundoApellido("Lobato")
				.nombreCompleto("Jesús Manuel Pérez Lobato")
				.build();
		log.debug("Persona por defecto: " + persona);
		return persona;
	}
	
	/**
	 * Devuelve la persona por defecto que crea la peticiones en el Redmine de Proxya.
	 *
	 * @return Objeto persona para el Redmine de Proxya
	 */
	@Bean
	Persona getProxyaDefaultPersona() {
		Persona persona = PersonaImpl.builder()
				.id("323")
				.apiKey("496ad4bc2a9a283b824065a176f01f76eb0623ec")
				.nickname("jmplobato")
				.nombre("Jesús Manuel")
				.primerApellido("Pérez")
				.segundoApellido("Lobato")
				.nombreCompleto("Jesús Manuel Pérez Lobato")
				.build();
		log.debug("Persona por defecto: {}", persona);
		return persona;
	}
	
	/**
	 * Obtiene el proyecto por defecto en caso de no tener ninguno en el repositorio de proyectos.
	 *
	 * @return Devuelve los datos del proyecto educamosCLM
	 */
	@Bean
	Proyecto getProyectoEducamos() {
		Proyecto proyecto = ProyectoImpl.builder()
				.descripcion("\"Formulario de acuerdo\":https://docs.google.com/spreadsheets/d/1m6lo__KHr3-NSGeoSkGT-5AAbIMkXCJBH4ImvzS_4eY/edit#gid=0\r\n"
						+ "\"Plan de proyecto\":https://docs.google.com/spreadsheets/d/1-kyFW_ClB_OntCHBJe5NNwQ_S-f7n43rMskHsOjONFM/edit#gid=0")
				.identificador("jccm-educamosclm")
				.nombre("JCCM - 2021 - EDUCAMOSCLM")
				.build();
		log.debug("Proyecto por defecto: {}", proyecto);
		return proyecto;
		
	}
	
	/**
	 * Gets the proyecto educamos gesproy.
	 *
	 * @return the proyecto educamos gesproy
	 */
	@Bean
	Proyecto getProyectoEducamosGesproy() {
		return ProyectoImpl.builder()
				.descripcion("Servicio de evolución de la plataforma educamosCLM (https://educamosclm.castillalamancha.es/)\n"
						+ "\n"
						+ "Comprende la dirección y seguimiento del proyecto. En concreto, el backlog de la plataforma y los sprint definidos para su implementación. Los trabajos asociados a cambios correctivos, evolutivos, perfectivos y adaptativo se gestionarán en sus respectivos proyectos. \n"
						+ "\n"
						+ "En la Wiki del proyecto se descrita la aproximación de gestión basado en metodología ágil que se adoptará para la plataforma.")
				.identificador("2404_v-edu-a-educamosclm-s-mantenimiento")
				.nombre("V-EDU-A EDUCAMOSCLM-S MANTENIMIENTO")
				.build();
	}
}
