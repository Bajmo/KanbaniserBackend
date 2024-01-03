package me.bajmo.kanbaniser.controllers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.bajmo.kanbaniser.entities.Board;
import me.bajmo.kanbaniser.entities.Task;
import me.bajmo.kanbaniser.exceptions.BoardNotFoundException;
import me.bajmo.kanbaniser.exceptions.TaskNotFoundException;
import me.bajmo.kanbaniser.exceptions.UserNotFoundException;
import me.bajmo.kanbaniser.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody Task task) {
        taskService.saveTask(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        try {
            Task task = taskService.getTaskRepository().findById(id)
                    .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found!"));
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = taskService.getTaskRepository().findAll();
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (BoardNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        try {
            taskService.updateTask(id, updatedTask);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<Void> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        try {
            taskService.assignTaskToUser(taskId, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TaskNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("/{taskId}/unassign")
    public ResponseEntity<Void> unassignTaskFromUser(@PathVariable Long taskId) {
        try {
            taskService.unassignTaskFromUser(taskId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
