package JAVALINGO.PROYECTO.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JAVALINGO.PROYECTO.MODEL.Persona;
import JAVALINGO.PROYECTO.REPOSITORY.PersonaRepository;

/**
 * Service class for managing personas.
 *
 * @author Roberto García Román
 */
@Service
public class PersonaService {
	
	@Autowired
	PersonaRepository personaRepository;

	/**
	 * Saves a persona.
	 *
	 * @param p The persona to be saved.
	 * @author Roberto García Román
	 */
	public void save(Persona p) {
		p.setIs_admin(0);
		p.setExperience(0);
		p.setLevel(1);
		personaRepository.saveAndFlush(p);
	}
	
	/**
	 * Saves an account for a persona.
	 *
	 * @param p The persona to create an account for.
	 * @author Roberto García Román
	 */
	public void save_account(Persona p) {
		p.setLevel(1);
		p.setExperience(0);
		personaRepository.saveAndFlush(p);
	}
	
	/**
	 * Deletes a persona by its ID.
	 *
	 * @param id The ID of the persona to be deleted.
	 * @author Roberto García Román
	 */
	public void delete_persona(Integer id) {
		personaRepository.deleteById(id);
	}

	/**
	 * Retrieves a persona by its ID.
	 *
	 * @param id The ID of the persona to retrieve.
	 * @return The persona object associated with the given ID, or null if not found.
	 * @author Roberto García Román
	 */
	public Persona getById(Integer id) {
		return personaRepository.getReferenceById(id);
	}
	
	/**
	 * Updates the experience of a persona.
	 *
	 * @param p The persona to update the experience for.
	 * @author Roberto García Román
	 */
	public void Experience(Persona p){
		personaRepository.saveAndFlush(p);
	}
}
