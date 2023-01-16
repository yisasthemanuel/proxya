package org.jlobato.imputaciones.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;

import lombok.extern.slf4j.Slf4j;


/**
 * The Class SETAConfiguracion.
 */
@Configuration
@EnableConfigurationProperties(RedMineProperties.class)

/** The Constant log. */

/** The Constant log. */
@Slf4j
public class RedMineConfiguracion {

	
	/**
	 * Gets the seta properties.
	 *
	 * @return the seta properties
	 */
	@ConfigurationProperties(prefix="redmine")
	@Bean
	public Map<String, Object> getRedMineProperties() {
	    return new HashMap<>();
	}
	
	/**
	 * Gets the http client.
	 *
	 * @return the http client
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	@Bean
	public HttpClient getHttpClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		//Configurar el cliente https
		SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(ResourceUtils.getFile("classpath:cacerts"), "changeit".toCharArray()).build();
		
		HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();		
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		
		log.debug("HttpClient:" + httpClient);
		
		return httpClient;
	}
	
	/**
	 * Gets the formateador horas.
	 *
	 * @return the formateador horas
	 */
	@Bean
	public NumberFormat getFormateadorHoras() {
		NumberFormat df = new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.ENGLISH));
		((DecimalFormat) df).setParseBigDecimal(true);
		return df;
	}
	
	@Bean
	public Locale getLocaleDefecto() {
		return new Locale("es", "ES");
	}
	
	/**
	 * Gets the formateador fecha.
	 *
	 * @return the formateador fecha
	 */
	@Bean("formateadorFecha")
	public SimpleDateFormat getFormateadorFecha() {
		return new SimpleDateFormat("MMMMM 'de' yyyy", getLocaleDefecto());
	}
	
	/**
	 * Gets the formateador fecha restauracion.
	 *
	 * @return the formateador fecha restauracion
	 */
	@Bean("formateadorFechaRestauracion")
	public SimpleDateFormat getFormateadorFechaRestauracion() {
		return new SimpleDateFormat("dd/MM/yyyy 'a las' HH:mm", getLocaleDefecto());
	}
	
	@Bean("formateadorFechaImputaciones")
	public SimpleDateFormat getDateFormatter() {
		return new SimpleDateFormat("dd/MM/yyyy");
	}
	
	
	@Bean("formateadorTextos")
	public MessageFormat getFormateadorTextos() {
		return new MessageFormat("", getLocaleDefecto());
	}
	
	
	/**
	 * Gets the imputaciones fields.
	 *
	 * @return the imputaciones fields
	 */
	@Bean
	public Map<String, String> getImputacionesFields() {
		HashMap<String, String> result = new HashMap<>();
		
		result.put("Horas dedicadas JA", "JAPermanente");
		result.put("Horas dedicadas AFS", "AFSPermanente");
		result.put("Horas dedicadas AP", "APPermanente");
		result.put("Horas dedicadas PR", "PRPermanente");
		
		result.put("Horas bolsa dedicadas JA", "JABolsa");
		result.put("Horas bolsa dedicadas AFS", "AFSBolsa");
		result.put("Horas bolsa dedicadas AP", "APBolsa");
		result.put("Horas bolsa dedicadas PR", "PRBolsa");
		
		return result;
	}
	
}
