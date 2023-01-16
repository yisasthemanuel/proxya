package org.jlobato.imputaciones.service.impl;

import org.jlobato.imputaciones.service.CargaImputacionesIDGenerator;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class SETACargaIDGeneratorImpl.
 */
@Service
public class CargaImputacionesIDGeneratorImpl implements CargaImputacionesIDGenerator {

	/**
	 * Gets the new ID.
	 *
	 * @return the new ID
	 */
	@Override
	public String getNewID() {
		return Long.toString(System.currentTimeMillis());
	}

}
