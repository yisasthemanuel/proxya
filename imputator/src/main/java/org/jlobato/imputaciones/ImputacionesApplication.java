package org.jlobato.imputaciones;

import org.jlobato.imputaciones.config.RedMineConfiguracion;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.impl.PersonaImpl;
import org.jlobato.imputaciones.service.ImputacionService;
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
	public CommandLineRunner initSetaService(ImputacionService imputacionService, RedMineConfiguracion config) {
		return args -> {
			imputacionService.init();
			log.info("Configuración: " + config.getRedMineProperties());
		};
	}
	
//	@Bean
//	public CommandLineRunner initRelacion(ImputacionService service, RedMineService tagetRedMine) {
//		return args -> {
//			Integer source = 158880;
//			
//			Integer[] targets = {
//					125922	,
//					125923	,
//					125924	,
//					125926	,
//					140992	,
//					143160	,
//					143161	,
//					143167	,
//					151823	,
//					151924	,
//					151926	,
//					153167	,
//					155893	,
//					156070	,
//					156086	,
//					156502	,
//					156513	,
//					156519	,
//					156587	,
//					156592	,
//					156722	,
//					157382	,
//					157452	,
//					157479	,
//					157886	,
//					158067	,
//					158168	,
//					158341	
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
	public Persona getDefaultPersona() {
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
	
//	@Bean
//	public CommandLineRunner readGoogleSheet() {
//		return args -> {
//			GoogleAuthorizeUtil.readSheet();
//		};
//	}
}
