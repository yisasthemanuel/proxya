package org.jlobato.imputaciones.model;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Interface RedMineTarget.
 */
public interface RedMine extends Serializable {
	
	/**
	 * Gets the uri.
	 *
	 * @return the uri
	 */
	public String getUri();
	
	/**
	 * Gets the api key.
	 *
	 * @return the api key
	 */
	public String getApiKey();
	
	/**
	 * Gets the project name.
	 *
	 * @return the project name
	 */
	public String getProjectName();
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription();
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId();

}
