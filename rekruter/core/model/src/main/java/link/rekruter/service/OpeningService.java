package link.rekruter.service;

import java.util.List;

import link.rekruter.model.Opening;
import link.rekruter.model.Stage;

public interface OpeningService {

	/**
	 * registers a new opening in the system
	 */
	void registerOpening(Opening opening);

	/**
	 * returns all opening (by default)
	 * 
	 * @return a list comprising of all openings
	 */
	List<Opening> getOpenings();

	/**
	 * update an existing opening
	 */
	void updateOpening();

	/**
	 * archive an opening; note that openings are never permanently deleted
	 */
	void archiveOpening();

	/**
	 * filter opening
	 * 
	 * @return a list of filtered opening
	 */
	List<Opening> filter();

	/**
	 * create a new opening by cloning an existing opening
	 * 
	 * @param existing opening
	 * @return new opening which is clone of the existing one
	 */
	Opening clone(Opening opening);

	/**
	 * associate an opening to a stage
	 * 
	 * @param stage   to be associated to
	 * @param opening in question
	 */
	void associateStage(Stage stage, Opening opening);

	/**
	 * approve an opening, in order to make it go past a stage
	 * 
	 * @param opening in question
	 */
	void approveOpening(Opening opening);

}
