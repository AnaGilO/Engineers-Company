package JAVALINGO.PROYECTO.MODEL;

import java.time.LocalDate;
//import java.util.Date;
import java.util.Objects;

//import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Persona {
	@Id
	@GeneratedValue	
	private Integer persona_pk;
	
	private String nombre;
	
	private LocalDate fecha_creacion;
	

	private String mail;
	
	private String password;
	
	private Integer is_admin;
	
	public Persona() {
		fecha_creacion = LocalDate.now();	
	}
	
	
	public Integer getIs_admin() {
		return is_admin;
	}

	public void setIs_admin(Integer is_admin) {
		this.is_admin = is_admin;
	}

	public Integer getPersona_pk() {
		return persona_pk;
	}
	public void setPersona_pk(int persona_pk) {
		this.persona_pk = persona_pk;
	}
	
	public LocalDate getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDate fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
		return Objects.hash(mail, nombre, password, persona_pk);
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
		return Objects.equals(mail, other.mail)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(password, other.password)
				&& persona_pk == other.persona_pk;
	}
	@Override
	public String toString() {
		return nombre + mail;
	}
}

