package JAVALINGO.PROYECTO.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JAVALINGO.PROYECTO.MODEL.Test_answers;
import JAVALINGO.PROYECTO.REPOSITORY.Test_answersRepository;

@Service
public class Test_answersService {
	
	@Autowired
	Test_answersRepository test_answersRepository;
	
	public void save(Test_answers answer) {
		test_answersRepository.saveAndFlush(answer);
	}

}
