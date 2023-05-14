package JAVALINGO.PROYECTO.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JAVALINGO.PROYECTO.MODEL.Exercises;
import JAVALINGO.PROYECTO.REPOSITORY.ExercisesRepository;

@Service
public class ExercisesService {
	
	@Autowired
	ExercisesRepository exercisesRepository;
	
	public void save(Exercises exercise) {
		exercisesRepository.saveAndFlush(exercise);
	}

	public void delete_persona(Integer id) {
		exercisesRepository.deleteById(id);		
	}

	public Object getById(Integer id) {
		return exercisesRepository.getReferenceById(id);
	}
}
