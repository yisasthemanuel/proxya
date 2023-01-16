package org.jlobato.imputaciones;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.jlobato.imputaciones.model.Estimacion;
import org.jlobato.imputaciones.repository.impl.EstimacionExcelReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EstimacionesExcelReaderTests {
	@Autowired
	EstimacionExcelReader reader;
	
	@Test
	public void readExcelEstimaciones() {
		String file = EstimacionesExcelReaderTests.class.getResource("/estimacion.modelo.xlsx").getFile(); 
		log.debug(file);
		
		reader.setPath(file);
		
		List<Estimacion> estimaciones = reader.getEstimaciones(); 
		log.info("Imputaciones leidas: " + estimaciones.size());
		log.info(estimaciones.toString());
		
		assertEquals(17, estimaciones.size());
	}
	
	@Test
	public void readExcelEstimacionesInputStream() {
		String file = EstimacionesExcelReaderTests.class.getResource("/estimacion.modelo.xlsx").getFile(); 
		log.debug(file);
		
		List<Estimacion> estimaciones = null;
		
		try (FileInputStream excelFile = new FileInputStream(file)) {
			estimaciones = reader.getEstimaciones(excelFile);
			log.info("Imputaciones leidas: " + estimaciones.size());
			log.info(estimaciones.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(estimaciones);
		assertEquals(17, estimaciones.size());
	}
}
