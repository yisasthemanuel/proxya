package org.jlobato.imputaciones.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jlobato.imputaciones.model.RedMine;
import org.jlobato.imputaciones.model.impl.RedMineTargetImpl;
import org.jlobato.imputaciones.service.RedMineService;
import org.springframework.stereotype.Service;

/**
 * The Class RedMineTargetDummyServiceImpl.
 */
@Service
public class RedMineDummyServiceImpl implements RedMineService {
	
	/** The Constant REDMINES. */
	private static final Map<Integer, RedMine> REDMINES = new HashMap<>();
	
	static {
		REDMINES.put(1, RedMineTargetImpl.builder()
				.id(1)
				.uri("http://localhost:3000/")
				.apiKey("5163fe4279c2aa388d63b4e215e12325ec9c3f79")
				.projectName("testing-project")
				.description("RedMine para hacer tests del microservicio. Simula la estructura del RedMine de CECEU (SETA)")
				.build()
		);
		
		REDMINES.put(2, RedMineTargetImpl.builder()
				.id(2)
				.uri("https://seta.ceceu.junta-andalucia.es/")
				.apiKey("a66efaf280719a1af270043610d94327e533edb9")
				.projectName("desarrollo")
				.description("SETA: RedMine de CECEU")
				.build()
		);
		
		REDMINES.put(3, RedMineTargetImpl.builder()
				.id(3)
				.uri("https://gesproy.jccm.es/")
				.apiKey("ed7838edbbe8c1cee88413349219ed6967c7a90e") //jmpl06
//				.apiKey("e280e5a8cb18357313a9d14108f33239ce1752cd") //mmcg165
				.projectName("desarrollo")
				.description("Gesproy: RedMine de educamosCLM")
				.build()
		);

		REDMINES.put(4, RedMineTargetImpl.builder()
				.id(4)
				.uri("https://redmine.emergya.com/")
				.apiKey("496ad4bc2a9a283b824065a176f01f76eb0623ec") //jmplobato
				.projectName("Proxya")
				.description("RedMine de Proxya")
				.build()
		);
		
		REDMINES.put(5, RedMineTargetImpl.builder()
				.id(5)
				.uri("https://redmine.ayuncordoba.org/")
				.apiKey("fceb749f2a53a294d7e2e4ec1e9e8c313a267ac6") //jmplobato
				.projectName("Ayto Córdoba")
				.description("RedMine del Ayuntamiento de Córdoba")
				.build()
		);
	}
	
	/**
	 * Gets the red mines availables.
	 *
	 * @return the red mines availables
	 */
	@Override
	public List<RedMine> getRedMinesAvailables() {
		return new ArrayList<>(REDMINES.values());
	}

	/**
	 * Adds the red mine.
	 *
	 * @param redmine the redmine
	 */
	@Override
	public void addRedMine(RedMine redmine) {
		REDMINES.put(redmine.getId(), redmine);
	}

	/**
	 * Gets the red mine.
	 *
	 * @param id the id
	 * @return the red mine
	 */
	@Override
	public RedMine getRedMine(int id) {
		return REDMINES.get(id);
	}

	/**
	 * Update red mine.
	 *
	 * @param redmine the redmine
	 */
	@Override
	public void updateRedMine(RedMine redmine) {
		REDMINES.put(redmine.getId(), redmine);
	}

	/**
	 * Delete red mine.
	 *
	 * @param id the id
	 */
	@Override
	public void deleteRedMine(int id) {
		REDMINES.remove(id);
	}

}
