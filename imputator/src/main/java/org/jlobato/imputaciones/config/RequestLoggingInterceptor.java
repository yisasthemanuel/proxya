package org.jlobato.imputaciones.config;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;

/** The Constant log. */
@Slf4j
public class RequestLoggingInterceptor implements HttpRequestInterceptor {

	/**
	 * Process.
	 *
	 * @param request the request
	 * @param context the context
	 * @throws HttpException the http exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
		log.debug("Solicitud HTTP: {}", request.getRequestLine());
		
		if (request instanceof HttpEntityEnclosingRequest
				&& ((HttpEntityEnclosingRequest) request).getEntity() != null
                && ((HttpEntityEnclosingRequest) request).getEntity().isRepeatable()) {

            HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();

            try {
                // Convierte el contenido de la entidad a una cadena
                log.debug("Cuerpo de la solicitud: {}", EntityUtils.toString(entity));
            } catch (Exception e) {
            	log.error("Error al obtener el cuerpo de la solicitud", e);
            }
        }
	}

}
