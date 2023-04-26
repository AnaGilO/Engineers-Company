package es.uma.ingsoftware.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test_answersTest {

private Test_answers testAnswers;
	
	@BeforeEach
	public void setUp() {
		testAnswers = new Test_answers();
		testAnswers.setAnswer_id(1);
		testAnswers.setQuestion_id(2);
		testAnswers.setAnswer("Sample Answer");
	}
	
	@Test
	public void testGetAnswerId() {
		assertEquals(1, testAnswers.getAnswer_id());
	}
	
	@Test
	public void testGetQuestionId() {
		assertEquals(2, testAnswers.getQuestion_id());
	}
	
	@Test
	public void testGetAnswer() {
		assertEquals("Sample Answer", testAnswers.getAnswer());
	}
	
	@Test
	public void testSetAnswer() {
		testAnswers.setAnswer("New Answer");
		assertEquals("New Answer", testAnswers.getAnswer());
	}
	
	@Test
	public void testSetQuestionId() {
		testAnswers.setQuestion_id(3);
		assertEquals(3, testAnswers.getQuestion_id());
	}
	
	@Test
	public void testSetAnswerId() {
		testAnswers.setAnswer_id(3);
		assertEquals(3, testAnswers.getAnswer_id());
	}
	
}
