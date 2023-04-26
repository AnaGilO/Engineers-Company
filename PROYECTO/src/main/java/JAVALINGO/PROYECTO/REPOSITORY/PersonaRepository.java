package JAVALINGO.PROYECTO.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;

import JAVALINGO.PROYECTO.MODEL.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {

}
