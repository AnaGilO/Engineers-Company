package JAVALINGO.PROYECTO.MODEL;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Exercises {
	@Id
	@GeneratedValue
	private Integer excercise_id;
	
	private String exercise_name;
	
	
	public Integer getExcercise_id() {
		return excercise_id;
	}
	public void setExcercise_id(Integer excercise_id) {
		this.excercise_id = excercise_id;
	}
	public String getExercise_name() {
		return exercise_name;
	}
	public void setExercise_name(String exercise_name) {
		this.exercise_name = exercise_name;
	}
	@Override
	public int hashCode() {
		return Objects.hash(excercise_id, exercise_name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exercises other = (Exercises) obj;
		return Objects.equals(excercise_id, other.excercise_id) && Objects.equals(exercise_name, other.exercise_name);
	}
	@Override
	public String toString() {
		return "Exercises [excercise_id=" + excercise_id + ", exercise_name=" + exercise_name + "]";
	}
	
}
