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
		p.setIs_admin(0);
		personaRepository.saveAndFlush(p);
	}
	
	public void save_account(Persona p) {
		personaRepository.saveAndFlush(p);
	}

}
