package Engineers.Company.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class daily_challenges {
	@Id
	@GeneratedValue	
	private int challenge_id;
	@Nonnull
	private String challenge;
	@Nonnull
	private String challenge_answer;
	
	private LocalDate challenge_day;

	public int getChallenge_id() {
		return challenge_id;
	}

	public void setChallenge_id(int challenge_id) {
		this.challenge_id = challenge_id;
	}

	public String getChallenge() {
		return challenge;
	}

	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}

	public String getChallenge_answer() {
		return challenge_answer;
	}

	public void setChallenge_answer(String challenge_answer) {
		this.challenge_answer = challenge_answer;
	}

	public LocalDate getChallenge_day() {
		return challenge_day;
	}

	public void setChallenge_day(LocalDate challenge_day) {
		this.challenge_day = challenge_day;
	}
	
	
	
}