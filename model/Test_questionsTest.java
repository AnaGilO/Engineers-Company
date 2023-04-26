package es.uma.ingsoftware.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test_questionsTest {

	private Test_questions testQuestion;

    @BeforeEach
    public void setUp() {
        testQuestion = new Test_questions();
        testQuestion.setQuestion_id(123);
        testQuestion.setQuestion("What is inheritance?");
        testQuestion.setCreated_date(LocalDate.now());
    }

    @Test
    public void testGetQuestion_id() {
        assertEquals(123, testQuestion.getQuestion_id());
    }

    @Test
    public void testGetQuestion() {
        assertEquals("What is inheritance?", testQuestion.getQuestion());
    }

    @Test
    public void testGetCreated_date() {
        assertEquals(LocalDate.now(), testQuestion.getCreated_date());
    }
    
    @Test
    public void testSetQuestion_id() {
        testQuestion.setQuestion_id(456);
        assertEquals(456, testQuestion.getQuestion_id());
    }

    @Test
    public void testSetQuestion() {
        testQuestion.setQuestion("What is a constructor?");
        assertEquals("What is a constructor?", testQuestion.getQuestion());
    }

    @Test
    public void testSetCreated_date() {
        LocalDate date = LocalDate.now().plusDays(1);
        testQuestion.setCreated_date(date);
        assertEquals(date, testQuestion.getCreated_date());
    }


    @Test
    public void testEquals() {
        Test_questions sameQuestion = new Test_questions();
        sameQuestion.setQuestion_id(123);
        sameQuestion.setQuestion("What is inheritance?");
        sameQuestion.setCreated_date(LocalDate.now());
        assertEquals(testQuestion, sameQuestion);
    }

}
