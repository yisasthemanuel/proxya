package org.jlobato.imputaciones.service.impl;

import org.jlobato.imputaciones.model.Estimacion;
import org.jlobato.imputaciones.model.Persona;
import org.jlobato.imputaciones.model.RedMine;
import org.jlobato.imputaciones.service.EstimacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EstimacionServiceImpl implements EstimacionService {
	/** The RedMine driver. */
	@Autowired
	private RedMineDriver redmineDriver;


	@Override
	public void init() {
		redmineDriver.init();
		log.info("EstimacionServiceImpl iniciado");
	}

	@Override
	public void tiempoEstimado(RedMine redmine, Persona persona, Integer issueId, Float horas) {
		redmineDriver.tiempoEstimado(redmine, persona, issueId, horas);
	}

	@Override
	public void tiempoEstimado(RedMine redmine, Estimacion estimacion) {
		redmineDriver.tiempoEstimado(redmine, estimacion);
	}

}
