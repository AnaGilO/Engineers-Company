package JAVALINGO.PROYECTO.SERVICE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JAVALINGO.PROYECTO.MODEL.Forum;
import JAVALINGO.PROYECTO.REPOSITORY.ForumRepository;

/**
 * Service class for managing forums.
 * 
 * @author Roberto García Román
 */
@Service
public class ForumService {
	@Autowired
	ForumRepository forumRepository;

	/**
	 * Saves a forum.
	 *
	 * @param forum The forum to be saved.
	 * @author Roberto García Román
	 */
	public void save(Forum forum) {
		forum.setVotes(0);
		forumRepository.saveAndFlush(forum);
	}
	
	/**
	 * Retrieves a forum by its ID.
	 *
	 * @param id The ID of the forum to retrieve.
	 * @return The forum object associated with the given ID, or null if not found.
	 * @author Roberto García Román
	 */
	public Forum getById(Integer id) {
		return forumRepository.getReferenceById(id);
	}
	
	/**
	 * Updates the votes of a forum.
	 *
	 * @param forum The forum to update the votes for.
	 * @param i The number of votes to add (positive or negative).
	 * @author Roberto García Román
	 */
	public void update_votes(Forum forum, int i) {
		forum.setVotes(forum.getVotes() + i);
		forumRepository.saveAndFlush(forum);
	}
	
	/**
	 * Deletes a forum by its ID.
	 *
	 * @param id The ID of the forum to be deleted.
	 * @author Roberto García Román
	 */
	public void delete_forum_questons(Integer id) {
		forumRepository.deleteById(id);
	}
}
