package org.jlobato.imputaciones.config;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;

import lombok.extern.slf4j.Slf4j;

/** The Constant log. */
@Slf4j
public class ResponseLoggingInterceptor implements HttpResponseInterceptor {

	/**
	 * Process.
	 *
	 * @param response the response
	 * @param context the context
	 * @throws HttpException the http exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
		log.debug("Respuesta HTTP: {}", response.getStatusLine());
	}

}
