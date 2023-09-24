package org.jlobato.imputaciones;

import org.jlobato.imputaciones.config.RedMineConfiguracion;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.impl.PersonaImpl;
import org.jlobato.imputaciones.service.ImputacionService;
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
