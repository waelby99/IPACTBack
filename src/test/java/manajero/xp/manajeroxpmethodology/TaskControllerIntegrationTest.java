package manajero.xp.manajeroxpmethodology;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Status;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Task;
import manajero.xp.manajeroxpmethodology.Repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    private Task task;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        task = Task.builder()
                .id("1")
                .title("Test Task")
                .description("Test Task Description")
                .status(Status.TO_DO)
                .build();

        taskRepository.save(task);
    }

    @Test
    public void testCreateTask() throws Exception {
        Task newTask = Task.builder()
                .id("2")
                .title("New Task")
                .description("New Task Description")
                .status(Status.IN_PROGRESS)
                .build();

        mockMvc.perform(post("/api/task/addTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.title").value("New Task"));

        System.out.println("Integration test for create task completed successfully");
    }

    @Test
    public void testGetTaskById() throws Exception {
        mockMvc.perform(get("/api/task/getTaskById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Task"));

        System.out.println("Integration test for get task by id completed successfully");
    }

    @Test
    public void testUpdateTask() throws Exception {
        task.setTitle("Updated Task Title");

        mockMvc.perform(put("/api/task/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Updated Task Title"));

        System.out.println("Integration test for update task completed successfully");
    }

    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/task/deleteTask/1"))
                .andExpect(status().isOk());

        System.out.println("Integration test for delete task completed successfully");
    }
}

