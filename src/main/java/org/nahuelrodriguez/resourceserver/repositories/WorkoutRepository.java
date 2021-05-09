package org.nahuelrodriguez.resourceserver.repositories;

import org.nahuelrodriguez.resourceserver.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    @Query("SELECT w FROM Workout w WHERE w.user = ?#{authentication.name}")
    List<Workout> findAllByUser();
}
