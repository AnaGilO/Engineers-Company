package JAVALINGO.PROYECTO.MODEL;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Test_questions {
	@Id
	@GeneratedValue	
	private Integer question_id;
	@Nonnull
	private String question;
	
	private Integer exercise_id;
	
	@Nonnull
	private LocalDate created_date;
	
	private Integer question_order;
	
	
	
	
	public Test_questions() {
		created_date = LocalDate.now();	
	}
	
	
	public Integer getQuestion_order() {
		return question_order;
	}





	public void setQuestion_order(Integer question_order) {
		this.question_order = question_order;
	}





	public Integer getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(Integer question_id) {
		this.question_id = question_id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public LocalDate getCreated_date() {
		return created_date;
	}
	public void setCreated_date(LocalDate created_date) {
		this.created_date = created_date;
	}
	
	public Integer getExercise_id() {
		return exercise_id;
	}
	public void setExercise_id(Integer exercise_id) {
		this.exercise_id = exercise_id;
	}
	@Override
	public int hashCode() {
		return Objects.hash(created_date, question, question_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test_questions other = (Test_questions) obj;
		return Objects.equals(created_date, other.created_date) && Objects.equals(question, other.question)
				&& question_id == other.question_id;
	}	
}

