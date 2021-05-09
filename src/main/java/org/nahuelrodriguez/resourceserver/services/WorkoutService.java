package org.nahuelrodriguez.resourceserver.services;

import org.nahuelrodriguez.resourceserver.entities.Workout;
import org.nahuelrodriguez.resourceserver.repositories.WorkoutRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    private final WorkoutRepository repository;

    public WorkoutService(final WorkoutRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("#workout.user == authentication.name")
    public void saveWorkout(final Workout workout) {
        repository.save(workout);
    }

    public List<Workout> findWorkouts() {
        return repository.findAllByUser();
    }

    public void deleteWorkout(final Integer id) {
        repository.deleteById(id);
    }
}
