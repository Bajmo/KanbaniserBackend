package me.bajmo.kanbaniser.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.bajmo.kanbaniser.entities.Task;
import me.bajmo.kanbaniser.entities.User;
import me.bajmo.kanbaniser.exceptions.TaskNotFoundException;
import me.bajmo.kanbaniser.exceptions.UserNotFoundException;
import me.bajmo.kanbaniser.repositories.TaskRepository;
import me.bajmo.kanbaniser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) throws TaskNotFoundException {
        taskRepository.deleteById(id);
    }

    public void updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found!"));
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCreatedAt(updatedTask.getCreatedAt());
        task.setCreatedBy(updatedTask.getCreatedBy());
        task.setSection(updatedTask.getSection());
        taskRepository.save(task);
    }

    @Transactional
    public void assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " not found!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found!"));
        task.setCreatedBy(user);
        taskRepository.save(task);
    }

    @Transactional
    public void unassignTaskFromUser(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " not found!"));
        task.setCreatedBy(null);
        taskRepository.save(task);
    }

    public JpaRepository<Task, Long> getTaskRepository() {
        return this.taskRepository;
    }

}
