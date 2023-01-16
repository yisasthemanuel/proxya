package org.jlobato.imputaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.jlobato.imputaciones.util.ParseadorHorasUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class ParseadorHorasUtilTests.
 */
@RunWith(SpringRunner.class)
@SpringBootTest

/** The Constant log. */
@Slf4j
public class ParseadorHorasUtilTests {
	
	/** The Constant VALUES_LOG_MESSAGE. */
	private static final String VALUES_LOG_MESSAGE = "Target {} - Value {}";

	/**
	 * Test formateo horas.
	 *
	 * @throws ParseException the parse exception
	 */
	@Test
	public void testFormateoHoras() throws ParseException {
		float target = 8.5f;		
		float value = ParseadorHorasUtil.parseaHoras("8.5");
		
		log.info(VALUES_LOG_MESSAGE, target, value);		
		assertEquals(Float.valueOf(target), Float.valueOf(value));
		
		target = 7.5f;		
		value = ParseadorHorasUtil.parseaHoras("7,5");
		
		log.info(VALUES_LOG_MESSAGE, target, value);		
		assertEquals(Float.valueOf(target), Float.valueOf(value));
		
		target = 1.75f;
		value = ParseadorHorasUtil.parseaHoras("1,75");
		
		log.info(VALUES_LOG_MESSAGE, target, value);		
		assertEquals(Float.valueOf(target), Float.valueOf(value));
		
		target = 1.753f;
		value = ParseadorHorasUtil.parseaHoras("1.753");
		
		log.info(VALUES_LOG_MESSAGE, target, value);		
		assertEquals(Float.valueOf(target), Float.valueOf(value));
	}

}
