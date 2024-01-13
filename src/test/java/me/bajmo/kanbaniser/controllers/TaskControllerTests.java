package me.bajmo.kanbaniser.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.bajmo.kanbaniser.entities.Task;
import me.bajmo.kanbaniser.entities.User;
import me.bajmo.kanbaniser.services.BoardService;
import me.bajmo.kanbaniser.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
class TaskControllerTests {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @Mock
    private BoardService boardService;

    @InjectMocks
    private TaskController taskController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(taskController).build();
    }

    private static Task getTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Sample Task");
        task.setDescription("Sample Description");
        task.setCreatedAt(new Date());

        User createdBy = new User();
        createdBy.setId(1L);
        createdBy.setFirstName("Imad");
        createdBy.setLastName("Maailil");
        createdBy.setPhoneNumber("1234567890");
        createdBy.setEmail("imad.maailil@example.com");
        createdBy.setPassword("password");

        task.setCreatedBy(createdBy);
        return task;
    }

    @Test
    void deleteTaskTest() throws Exception {
        int taskId = 1;

        doNothing().when(taskService).deleteTask((long) eq(taskId));

        mockMvc.perform(delete("/api/tasks/" + taskId))
                .andExpect(status().isOk());
    }

    @Test
    void updateTaskTest() throws Exception {
        int taskId = 1;
        Task task = new Task();
        task.setId((long) taskId);
        task.setTitle("Updated Task");
        task.setDescription("Updated Description");
        task.setCreatedAt(new Date());

        doNothing().when(taskService).updateTask((long) eq(taskId), any(Task.class));

        mockMvc.perform(put("/api/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk());
    }
    @Test
    void getAllTasksTest() throws Exception {
        User createdBy = new User();
        createdBy.setId(1L);
        createdBy.setFirstName("Imad");
        createdBy.setLastName("Maailil");
        createdBy.setPhoneNumber("1234567890");
        createdBy.setEmail("imad.maailil@example.com");
        createdBy.setPassword("password");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Sample Task");
        task.setDescription("Sample Description");
        task.setCreatedAt(new Date());
        task.setCreatedBy(createdBy);

        List<Task> tasks = List.of(task);

        when(taskService.getTaskRepository().findAll()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(tasks)));
    }

    @Test
    void getTaskByIdTest() throws Exception {
        int taskId = 1;

        User createdBy = new User();
        createdBy.setId(1L);
        createdBy.setFirstName("Imad");
        createdBy.setLastName("Maailil");
        createdBy.setPhoneNumber("1234567890");
        createdBy.setEmail("imad.maailil@example.com");
        createdBy.setPassword("password");

        Task task = new Task();
        task.setId((long) taskId);
        task.setTitle("Sample Task");
        task.setDescription("Sample Description");
        task.setCreatedAt(new Date());
        task.setCreatedBy(createdBy);

        when(taskService.getTaskRepository().findById((long) eq(taskId))).thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/v1/tasks/" + taskId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(task)));
    }

}