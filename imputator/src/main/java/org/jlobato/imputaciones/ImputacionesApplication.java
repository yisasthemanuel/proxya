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
	
	@Bean
	CommandLineRunner addPersona(PersonaRepository repository) {
		return args -> {
			
//			repository.addPersona(PersonaImpl.builder()
//					.id("1001")
//					.apiKey("41da33a93b02a095c9838be5f5d0e83a72605277")
//					.nickname("ffrm64")
//					.nombre("Francisco")
//					.primerApellido("Rodríguez")
//					.segundoApellido("Mudarra")
//					.nombreCompleto("Francisco Rodriguez Mudarra")
//					.build()
//					);
//
//			repository.addPersona(PersonaImpl.builder()
//					.id("1002")
//					.apiKey("569474ff307007e143605fbbf09c28ecaac29a62")
//					.nickname("ookk01")
//					.nombre("Ozlem")
//					.primerApellido("Kutukcu")
//					.segundoApellido("")
//					.nombreCompleto("Ozlem Kutukcu")
//					.build()
//					);

//			repository.updatePersona("mtll06", "1a5d97a3c09e6608ec9dcfd5ad920bd721110080", "María Teresa", "Lahoz", "López", "María Teresa Lahoz López");
			Iterator<Persona> personas = repository.getAllPersonas().iterator();
			
			while (personas.hasNext()) {
				log.info("PERSONA[{}]", personas.next());
			}
			
		};
	}


//	@Bean
//	public CommandLineRunner initRelacion(ImputacionService service, RedMineService tagetRedMine) {
//		return args -> {
//			Integer source = 164731;
//			
//			Integer[] targets = {
//					131753	,
//					141090	,
//					143159	,
//					143161	,
//					145629	,
//					147450	,
//					147458	,
//					150382	,
//					150384	,
//					150389	,
//					151160	,
//					151161	,
//					151165	,
//					151167	,
//					151923	,
//					151924	,
//					151925	,
//					152490	,
//					152800	,
//					153167	,
//					153977	,
//					155049	,
//					155247	,
//					155476	,
//					155893	,
//					156070	,
//					156086	,
//					156519	,
//					157242	,
//					157244	,
//					157515	,
//					157628	,
//					157886	,
//					157897	,
//					157899	,
//					157901	,
//					158067	,
//					158094	,
//					158132	,
//					158168	,
//					158449	,
//					158617	,
//					158736	,
//					158738	,
//					158739	,
//					158755	,
//					158840	,
//					158914	,
//					158920	,
//					159018	,
//					159062	,
//					159140	,
//					159196	,
//					159205	,
//					159336	,
//					159357	,
//					159358	,
//					159359	,
//					159377	,
//					159380	,
//					159385	,
//					159479	,
//					159511	,
//					159606	,
//					159608	,
//					160021	
//			};
//			
//			for (int i = 0; i < targets.length; i++) {
//				service.creaRelacion(tagetRedMine.getRedMine(3), getDefaultPersona(), source, targets[i]);
//				log.info("Relacionados: En RedMine {} por la persona {} la petición {} con la petición {}", tagetRedMine.getRedMine(3).getUri(), getDefaultPersona().getNombreCompleto(), source, targets[i]);
//			}
//			
//			log.info("######");
//			log.info("Fin del proceso de relación de peticiones. Se han relacionado {} peticiones a la petición {}", targets.length, source);
//			log.info("######");
//			
//		};
//	}
    
//	@Bean
//	public CommandLineRunner initImputacion(ImputacionService service, RedMineTargetService targetRedMineService, SimpleDateFormat formateadorFechaImputaciones) {
//		return args -> {
//			log.info("Imputando: " + service);
//			Persona persona = PersonaImpl.builder()
//					.id("23")
//					.apiKey("ed7838edbbe8c1cee88413349219ed6967c7a90e")
//					.nickname("jmpl06")
//					.nombre("Jesús Manuel")
//					.primerApellido("Pérez")
//					.segundoApellido("Lobato")
//					.nombreCompleto("Jesús Manuel Pérez Lobato")
//					.build();
//			ImputacionIndividual imputacion = ImputacionIndividualImpl.builder()
//					.id(94659)
//					.persona(persona)
//					.fecha(formateadorFechaImputaciones.parse("18/10/2021"))
//					.horas(8.5f)
//					.actividad(34)
//					.comentario("Seguimiento y gestión")
//					.build();
//			
//			service.tiempoDedicado(targetRedMineService.getRedMine(3), imputacion);
//					
//			log.info("Imputado: {}", persona);
//		};
//	}
    
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
	
	
//	@Bean
//	public CommandLineRunner readGoogleSheet() {
//		return args -> {
//			GoogleAuthorizeUtil.readSheet();
//		};
//	}
}
