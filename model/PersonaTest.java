package es.uma.ingsoftware.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonaTest {

	 private Persona persona;

	    @BeforeEach
	    public void setUp() {
	        persona = new Persona();
	        persona.setPersona_pk(1);
	        persona.setNombre("Alice Smith");
	        persona.setFecha_creacion(LocalDate.of(2023, 4, 26));
	        persona.setMail("alice1234@example.com");
	        persona.setPassword("password123");
	    }

	    @Test
	    public void testGetPersona_pk() {
	        assertEquals(1, persona.getPersona_pk());
	    }

	    @Test
	    public void testSetPersona_pk() {
	        persona.setPersona_pk(2);
	        assertEquals(2, persona.getPersona_pk());
	    }

	    @Test
	    public void testGetNombre() {
	        assertEquals("Alice Smith", persona.getNombre());
	    }

	    @Test
	    public void testSetNombre() {
	        persona.setNombre("Andrew Smith");
	        assertEquals("Andrew Smith", persona.getNombre());
	    }

	    @Test
	    public void testGetFecha_creacion() {
	        assertEquals(LocalDate.of(2023, 4, 26), persona.getFecha_creacion());
	    }

	    @Test
	    public void testSetFecha_creacion() {
	        persona.setFecha_creacion(LocalDate.of(2023, 4, 26));
	        assertEquals(LocalDate.of(2023, 4, 26), persona.getFecha_creacion());
	    }

	    @Test
	    public void testGetMail() {
	        assertEquals("alice1234@example.com", persona.getMail());
	    }

	    @Test
	    public void testSetMail() {
	        persona.setMail("smith12@example.com");
	        assertEquals("smith12@example.com", persona.getMail());
	    }

	    @Test
	    public void testGetPassword() {
	        assertEquals("password123", persona.getPassword());
	    }

	    @Test
	    public void testSetPassword() {
	        persona.setPassword("newpassword456");
	        assertEquals("newpassword456", persona.getPassword());
	    }

	    @Test
	    public void testHashCode() {
	        assertNotNull(persona.hashCode());
	    }

	    @Test
	    public void testEquals() {
	        Persona persona2 = new Persona();
	        persona2.setPersona_pk(1);
	        persona2.setNombre("Alice Smith");
	        persona2.setFecha_creacion(LocalDate.of(2023, 4, 26));
	        persona2.setMail("alice1234@example.com");
	        persona2.setPassword("password123");

	        assertTrue(persona.equals(persona2));
	    }

	    @Test
	    public void testNotEquals() {
	        Persona persona2 = new Persona();
	        persona2.setPersona_pk(2);
	        persona2.setNombre("Andrew Smith");
	        persona2.setFecha_creacion(LocalDate.of(2023, 4, 26));
	        persona2.setMail("smith12@example.com");
	        persona2.setPassword("newpassword456");

	        assertFalse(persona.equals(persona2));
	    }

	    @Test
	    public void testToString() {
	        assertEquals("Alice Smithalice1234@example.com", persona.toString());
	    }
	
}
