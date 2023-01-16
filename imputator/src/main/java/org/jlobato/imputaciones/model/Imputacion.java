package org.jlobato.imputaciones.model;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Interface Imputacion.
 */
public interface Imputacion extends Serializable {
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId();
	
	/**
	 * Gets the mes.
	 *
	 * @return the mes
	 */
	public int getMes();
	
	/**
	 * Gets the anho.
	 *
	 * @return the anho
	 */
	public int getAnho();
	
	/**
	 *  The JA permanente.
	 *
	 * @return the JA permanente
	 */
	public String getJAPermanente();
	
	/**
	 *  The AFS permanente.
	 *
	 * @return the AFS permanente
	 */
	public String getAFSPermanente();
	
	/**
	 *  The AP permanente.
	 *
	 * @return the AP permanente
	 */
	public String getAPPermanente();
	
	/**
	 *  The PR permanente.
	 *
	 * @return the PR permanente
	 */
	public String getPRPermanente();
	
	/**
	 *  The JA bolsa.
	 *
	 * @return the JA bolsa
	 */
	public String getJABolsa();
	
	/**
	 *  The AFS bolsa.
	 *
	 * @return the AFS bolsa
	 */
	public String getAFSBolsa();
	
	/**
	 *  The AP bolsa.
	 *
	 * @return the AP bolsa
	 */
	public String getAPBolsa();
	
	/**
	 *  The PR bolsa.
	 *
	 * @return the PR bolsa
	 */
	public String getPRBolsa();

}
