package org.jlobato.imputaciones.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.ToString;

/**
 * The Class SETAProperties.
 *
 * @author jmplobato
 */
@ConfigurationProperties(prefix="redmine")

/**
 * To string.
 *
 * @return the java.lang. string
 */

/**
 * To string.
 *
 * @return the java.lang. string
 */
@ToString
public class RedMineProperties {
	
	/** The uri. */
	private String uri;
	
	/** The api access key. */
	private String apiAccessKey;
	
	/** The project key. */
	private String projectKey;	
	
	/**
	 * Gets the uri SETA.
	 *
	 * @return the uri SETA
	 */
	public String getUri() {
		return uri;
	}
	
	/**
	 * Sets the uri SETA.
	 *
	 * @param uri the new uri
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	/**
	 * Gets the api access key SETA.
	 *
	 * @return the api access key SETA
	 */
	public String getApiAccessKey() {
		return apiAccessKey;
	}
	
	/**
	 * Sets the api access key SETA.
	 *
	 * @param apiAccessKey the new api access key
	 */
	public void setApiAccessKey(String apiAccessKey) {
		this.apiAccessKey = apiAccessKey;
	}
	
	/**
	 * Gets the project key SETA.
	 *
	 * @return the project key SETA
	 */
	public String getProjectKey() {
		return projectKey;
	}
	
	/**
	 * Sets the project key SETA.
	 *
	 * @param projectKey the new project key
	 */
	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}	
}
