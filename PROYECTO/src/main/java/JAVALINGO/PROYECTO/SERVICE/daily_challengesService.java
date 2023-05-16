package JAVALINGO.PROYECTO.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JAVALINGO.PROYECTO.MODEL.daily_challenges;
import JAVALINGO.PROYECTO.REPOSITORY.daily_challengesRepository;

@Service
public class daily_challengesService {
	
	@Autowired
	daily_challengesRepository daily_challengesRepository;
	
	public void save(daily_challenges challenge) {
		daily_challengesRepository.saveAndFlush(challenge);
	}

	public void delete_challenge(Integer id) {
		daily_challengesRepository.deleteById(id);
	}

	public daily_challenges getById(Integer id) {
		daily_challenges c = daily_challengesRepository.getReferenceById(id);
		return c;
	}
}
