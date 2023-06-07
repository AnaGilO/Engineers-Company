package JAVALINGO.PROYECTO.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JAVALINGO.PROYECTO.MODEL.Test_questions;
import JAVALINGO.PROYECTO.REPOSITORY.Test_questionsRepository;

/**
 * Service class for managing test questions.
 * 
 * @author Roberto García Román
 */
@Service
public class Test_questionsService {
	
	@Autowired
	Test_questionsRepository test_questionsRepository;
	
	/**
	 * Saves a test question.
	 * 
	 * @param question The test question to be saved.
	 * @author Roberto García Román
	 */
	public void save(Test_questions question) {
		test_questionsRepository.saveAndFlush(question);
	}
	
	/**
	 * Retrieves a test question by its ID.
	 * 
	 * @param question_id The ID of the test question to retrieve.
	 * @return The test question object associated with the given ID, or null if not found.
	 * @author Roberto García Román
	 */
	public Test_questions getById(Integer question_id) {
		return test_questionsRepository.getReferenceById(question_id);
	}
}
