package JAVALINGO.PROYECTO.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JAVALINGO.PROYECTO.MODEL.Exercises;
import JAVALINGO.PROYECTO.REPOSITORY.ExercisesRepository;

/**
 * Service class for managing exercises.
 *
 * @author Roberto García Román
 */
@Service
public class ExercisesService {
	
	@Autowired
	ExercisesRepository exercisesRepository;
	
	/**
	 * Saves an exercise.
	 *
	 * @param exercise The exercise to be saved.
	 * @author Roberto García Román
	 */
	public void save(Exercises exercise) {
		exercisesRepository.saveAndFlush(exercise);
	}

	/**
	 * Deletes an exercise by its ID.
	 *
	 * @param id The ID of the exercise to be deleted.
	 * @author Roberto García Román
	 */
	public void delete_persona(Integer id) {
		exercisesRepository.deleteById(id);		
	}

	/**
	 * Retrieves an exercise by its ID.
	 *
	 * @param id The ID of the exercise to retrieve.
	 * @return The exercise object associated with the given ID.
	 * @author Roberto García Román
	 */
	public Object getById(Integer id) {
		return exercisesRepository.getReferenceById(id);
	}
}
