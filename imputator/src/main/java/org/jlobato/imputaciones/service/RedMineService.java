package org.jlobato.imputaciones.service;

import java.util.List;

import org.jlobato.imputaciones.model.RedMine;

/**
 * The Interface RedMineTargetService.
 */
public interface RedMineService {
	
	/**
	 * Gets the red mines availables.
	 *
	 * @return the red mines availables
	 */
	public List<RedMine> getRedMinesAvailables();
	
	/**
	 * Adds the red mine.
	 *
	 * @param redmine the redmine
	 */
	public void addRedMine(RedMine redmine);
	
	/**
	 * Gets the red mine.
	 *
	 * @param id the id
	 * @return the red mine
	 */
	public RedMine getRedMine(int id);
	
	/**
	 * Update red mine.
	 *
	 * @param redmine the redmine
	 */
	public void updateRedMine(RedMine redmine);
	
	/**
	 * Delete red mine.
	 *
	 * @param id the id
	 */
	public void deleteRedMine(int id);

}
