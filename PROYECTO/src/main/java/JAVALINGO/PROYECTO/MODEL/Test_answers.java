package JAVALINGO.PROYECTO.MODEL;

import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
/**
 * This class is how I generate the Test Answers table in the database
 * @author Roberto García Román
 *
 */
@Entity
public class Test_answers {
	@Id
	@GeneratedValue	
	private Integer answer_id;
	/*
	@Nonnull
	@ManyToOne
	@JoinColumn(name="question_id")
	private Test_questions question_lista;
	*/
	
	private Integer question_id;
	
	@Nonnull
	private String Answer;
	
	private Integer is_correct;
	
	public Integer getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(Integer question_id) {
		this.question_id = question_id;
	}
	public Integer getIs_correct() {
		return is_correct;
	}
	public void setIs_correct(Integer is_correct) {
		this.is_correct = is_correct;
	}
	/*
	public Test_questions getQuestion_lista() {
		return question_lista;
	}
	public void setQuestion_lista(Test_questions question_lista) {
		this.question_lista = question_lista;
	}
	*/
	public Integer getAnswer_id() {
		return answer_id;
	}
	public void setAnswer_id(Integer answer_id) {
		this.answer_id = answer_id;
	}
	
	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	
}
