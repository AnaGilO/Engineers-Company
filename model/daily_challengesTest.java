package es.uma.ingsoftware.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class daily_challengesTest {
	
private daily_challenges challenge;
	
	@BeforeEach
	void setUp() {
		challenge = new daily_challenges();
	}
	
	@Test
	void testChallengeId() {
		challenge.setChallenge_id(1);
		assertEquals(1, challenge.getChallenge_id());
	}
	
	@Test
	void testChallenge() {
		challenge.setChallenge("Write a JUnit test");
		assertEquals("Write a JUnit test", challenge.getChallenge());
	}
	
	@Test
	void testChallengeAnswer() {
		challenge.setChallenge_answer("Write a JUnit test");
		assertEquals("Write a JUnit test", challenge.getChallenge_answer());
	}
	
	@Test
	void testChallengeDay() {
		LocalDate date = LocalDate.now();
		challenge.setChallenge_day(date);
		assertEquals(date, challenge.getChallenge_day());
	}
}
	

