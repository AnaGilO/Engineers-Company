package Engineers.Company.model;

import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Test_answers {
	@Id
	@GeneratedValue	
	private int answer_id;
	@Nonnull
	private int question_id;
	@Nonnull
	private String Answer;
	
	
	public int getAnswer_id() {
		return answer_id;
	}
	public void setAnswer_id(int answer_id) {
		this.answer_id = answer_id;
	}
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	
}
