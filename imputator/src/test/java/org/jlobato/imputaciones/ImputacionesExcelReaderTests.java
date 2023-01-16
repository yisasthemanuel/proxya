package org.jlobato.imputaciones;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.jlobato.imputaciones.model.Imputacion;
import org.jlobato.imputaciones.repository.impl.ImputacionExcelReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ImputacionesExcelReaderTests {
	
	@Test
	public void readExcelCalculadora12() {
		String file = ImputacionesExcelReaderTests.class.getResource("/Calculadora12.xlsx").getFile(); 
		log.debug(file);
		
		ImputacionExcelReader reader = new ImputacionExcelReader();
		reader.setPath(file);
		
		List<Imputacion> imputaciones = reader.getImputaciones(); 
		log.info("Imputaciones leidas: " + imputaciones.size());
		log.info(imputaciones.toString());
		
		assertEquals(36, imputaciones.size());
	}
	
	@Test
	public void readExcelCalculadora12InputStream() {
		String file = ImputacionesExcelReaderTests.class.getResource("/Calculadora12.xlsx").getFile(); 
		log.debug(file);
		
		List<Imputacion> imputaciones = null;
		ImputacionExcelReader reader = new ImputacionExcelReader();
		
		try (FileInputStream excelFile = new FileInputStream(file)) {
			imputaciones = reader.getImputaciones(excelFile);
			log.info("Imputaciones leidas: " + imputaciones.size());
			log.info(imputaciones.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(imputaciones);
		assertEquals(36, imputaciones.size());
	}
}
