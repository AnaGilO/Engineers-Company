package JAVALINGO.PROYECTO.MODEL;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class test {
	@Id
	@GeneratedValue
	private Integer test_pk;
	private String nombre;
	
	public test() {
		
	}

	public Integer getTest_pk() {
		return test_pk;
	}

	public void setTest_pk(Integer test_pk) {
		this.test_pk = test_pk;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre, test_pk);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		test other = (test) obj;
		return Objects.equals(nombre, other.nombre) && Objects.equals(test_pk, other.test_pk);
	}

	@Override
	public String toString() {
		return "test [test_pk=" + test_pk + ", nombre=" + nombre + "]";
	}
	
}
