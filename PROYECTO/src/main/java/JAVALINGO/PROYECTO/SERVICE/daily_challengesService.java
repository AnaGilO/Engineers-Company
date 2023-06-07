package JAVALINGO.PROYECTO.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JAVALINGO.PROYECTO.MODEL.daily_challenges;
import JAVALINGO.PROYECTO.REPOSITORY.daily_challengesRepository;

/**
 * Service class for managing daily challenges.
 * @author Roberto García Román
 */
@Service
public class daily_challengesService {
	
	@Autowired
	daily_challengesRepository daily_challengesRepository;
	
	/**
	 * Saves a daily challenge.
	 *
	 * @param challenge The daily challenge to be saved.
	 * @author Roberto García Román
	 */
	public void save(daily_challenges challenge) {
		daily_challengesRepository.saveAndFlush(challenge);
	}

	/**
	 * Deletes a daily challenge by its ID.
	 *
	 * @param id The ID of the daily challenge to be deleted.
	 * @author Roberto García Román
	 */
	public void delete_challenge(Integer id) {
		daily_challengesRepository.deleteById(id);
	}

	/**
	 * Retrieves a daily challenge by its ID.
	 *
	 * @param id The ID of the daily challenge to retrieve.
	 * @return The daily challenge object associated with the given ID.
	 * @author Roberto García Román
	 */
	public daily_challenges getById(Integer id) {
		daily_challenges c = daily_challengesRepository.getReferenceById(id);
		return c;
	}
}
