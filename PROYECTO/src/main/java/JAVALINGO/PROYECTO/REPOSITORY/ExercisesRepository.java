package JAVALINGO.PROYECTO.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;

import JAVALINGO.PROYECTO.MODEL.Exercises;

public interface ExercisesRepository extends JpaRepository<Exercises, Integer> {

}