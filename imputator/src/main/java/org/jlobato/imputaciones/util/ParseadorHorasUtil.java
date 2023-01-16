package org.jlobato.imputaciones.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ParseadorHorasUtil {
	private ParseadorHorasUtil() {
	}
	
	public static float parseaHoras(String value) throws ParseException {
		NumberFormat dfEnglish = new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.ENGLISH));
		((DecimalFormat) dfEnglish).setParseBigDecimal(true);
		
		return dfEnglish.parse(value.replace(",", ".")).floatValue();
	}
}
