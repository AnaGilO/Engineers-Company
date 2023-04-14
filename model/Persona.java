package Engineers.Company.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Persona {
	@Id
	@GeneratedValue	
	private int persona_pk;
	@Nonnull
	private String nombre;
	@Nonnull
	private LocalDate fecha_creacion;
	@Nonnull
	private String mail;
	@Nonnull
	private String password;
	
	public Persona() {
	
		
	}
	
	public int getPersona_pk() {
		return persona_pk;
	}
	public void setPersona_pk(int persona_pk) {
		this.persona_pk = persona_pk;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public LocalDate getFecha_creacion() {
		return fecha_creacion;
	}
	public void setFecha_creacion(LocalDate fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(fecha_creacion, mail, nombre, password, persona_pk);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(fecha_creacion, other.fecha_creacion) && Objects.equals(mail, other.mail)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(password, other.password)
				&& persona_pk == other.persona_pk;
	}
	@Override
	public String toString() {
		return nombre + mail;
	}
}

