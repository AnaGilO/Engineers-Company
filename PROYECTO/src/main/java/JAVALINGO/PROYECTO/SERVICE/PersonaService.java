package JAVALINGO.PROYECTO.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JAVALINGO.PROYECTO.MODEL.Persona;
import JAVALINGO.PROYECTO.REPOSITORY.PersonaRepository;

@Service
public class PersonaService {
	
	@Autowired
	PersonaRepository personaRepository;

	public void save(Persona p) {
		p.setIs_admin(1);
		p.setExperience(0);
		p.setLevel(1);
		personaRepository.saveAndFlush(p);
	}
	
	public void save_account(Persona p) {
		p.setLevel(1);
		p.setExperience(0);
		personaRepository.saveAndFlush(p);
	}
	
	public void delete_persona (Integer id) {
		personaRepository.deleteById(id);
	}

	public Persona getById(Integer id) {
		return personaRepository.getReferenceById(id);
	}
	
	public void Experience(Persona p) {
		personaRepository.saveAndFlush(p);
	}
}
