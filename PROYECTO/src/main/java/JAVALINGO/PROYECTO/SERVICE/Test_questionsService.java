package JAVALINGO.PROYECTO.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JAVALINGO.PROYECTO.MODEL.Test_questions;
import JAVALINGO.PROYECTO.REPOSITORY.Test_questionsRepository;

@Service
public class Test_questionsService {
	
	@Autowired
	Test_questionsRepository test_questionsRepository;
	
	public void save(Test_questions question) {
		test_questionsRepository.saveAndFlush(question);
	}

}
