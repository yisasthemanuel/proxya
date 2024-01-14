package org.jlobato.imputaciones;

import java.util.Iterator;

import org.jlobato.imputaciones.config.RedMineConfiguracion;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.Proyecto;
import org.jlobato.imputaciones.model.impl.PersonaImpl;
import org.jlobato.imputaciones.model.impl.ProyectoImpl;
import org.jlobato.imputaciones.repository.PersonaRepository;
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
	 * Inicialización del servicio SETA.
	 *
	 * @param imputacionService Servicio SETA que se va a iniciar
	 * @param config the config
	 * @return the command line runner
	 */
	@Bean
	CommandLineRunner initSetaService(ImputacionService imputacionService, RedMineConfiguracion config) {
		return args -> {
			imputacionService.init();
			log.info("Configuración: {}",  config.getRedMineProperties());
		};
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
//			List<Peticion> peticiones = peticionService.creaPeticionesMensuales(2024, 1, 8, redmineService.getRedMine(4), proyectoService.getProyectoByIdentificador("jccm-educamosclm"));
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
}
