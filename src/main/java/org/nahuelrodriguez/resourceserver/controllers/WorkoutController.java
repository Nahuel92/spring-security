package org.nahuelrodriguez.resourceserver.controllers;

import org.nahuelrodriguez.resourceserver.entities.Workout;
import org.nahuelrodriguez.resourceserver.services.WorkoutService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/workout")
public class WorkoutController {
    private final WorkoutService service;

    public WorkoutController(final WorkoutService service) {
        this.service = service;
    }

    @PostMapping
    public void saveWorkout(@RequestBody final Workout workout) {
        service.saveWorkout(workout);
    }

    @GetMapping
    public List<Workout> findAllByUser() {
        return service.findWorkouts();
    }

    @DeleteMapping("/{id}")
    public void deleteWorkout(@PathVariable final Integer id) {
        service.deleteWorkout(id);
    }
}
