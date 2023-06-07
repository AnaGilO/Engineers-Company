package JAVALINGO.PROYECTO.SERVICE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JAVALINGO.PROYECTO.MODEL.Test_answers;
import JAVALINGO.PROYECTO.REPOSITORY.Test_answersRepository;

/**
 * Service class for managing test answers.
 *
 * @author Roberto García Román
 */
@Service
public class Test_answersService {

    @Autowired
    Test_answersRepository test_answersRepository;

    /**
     * Saves a test answer.
     *
     * @param answer The test answer to be saved.
     * @author Roberto García Román
     */
    public void save(Test_answers answer) {
        test_answersRepository.saveAndFlush(answer);
    }

    /**
     * Retrieves a test answer by its ID.
     *
     * @param answer_id The ID of the test answer to retrieve.
     * @return The test answer object associated with the given ID, or null if not found.
     * @author Roberto García Román
     */
    public Test_answers getById(Integer answer_id) {
        return test_answersRepository.getReferenceById(answer_id);
    }

}
