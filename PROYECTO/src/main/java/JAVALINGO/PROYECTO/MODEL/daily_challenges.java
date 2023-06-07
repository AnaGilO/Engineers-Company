package JAVALINGO.PROYECTO.MODEL;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
/**
 * This class is how I generate the daily challenge table in the database
 * @author Roberto García Román
 *
 */
@Entity
public class daily_challenges {
	@Id
	@GeneratedValue	
	private Integer challenge_id;
	
	private String challenge;
	
	private String challenge_answer;
	
	private Integer is_correct;
	
	private LocalDate challenge_day;
	
	private Integer challenge_parent;
	
	private Integer experience;
	
	
	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public Integer getChallenge_parent() {
		return challenge_parent;
	}

	public void setChallenge_parent(Integer challenge_parent) {
		this.challenge_parent = challenge_parent;
	}

	public Integer getIs_correct() {
		return is_correct;
	}

	public void setIs_correct(Integer is_correct) {
		this.is_correct = is_correct;
	}

	public Integer getChallenge_id() {
		return challenge_id;
	}

	public void setChallenge_id(Integer challenge_id) {
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