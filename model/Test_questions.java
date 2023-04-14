package Engineers.Company.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Test_questions {
	@Id
	@GeneratedValue	
	private int question_id;
	@Nonnull
	private String question;
	@Nonnull
	private LocalDate created_date;
	
	
	
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
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

