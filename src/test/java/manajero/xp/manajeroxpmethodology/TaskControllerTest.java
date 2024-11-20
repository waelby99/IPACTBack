package manajero.xp.manajeroxpmethodology;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import manajero.xp.manajeroxpmethodology.Controller.TaskController;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Status;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Task;
import manajero.xp.manajeroxpmethodology.Services.ITaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITaskService iTaskService;

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
    }

    @Test
    public void testCreateTask() throws Exception {
        Mockito.when(iTaskService.addTask(Mockito.any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/task/addTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Task"));

        System.out.println("testCreateTask completed successfully");
    }

    @Test
    public void testGetTaskById() throws Exception {
        Mockito.when(iTaskService.getTaskById("1")).thenReturn(task);

        mockMvc.perform(get("/api/task/getTaskById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Task"));

        System.out.println("testGetTaskById completed successfully");
    }

    @Test
    public void testUpdateTask() throws Exception {
        Mockito.when(iTaskService.updateTask(Mockito.any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/task/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Task"));

        System.out.println("testUpdateTask completed successfully");
    }

    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/task/deleteTask/1"))
                .andExpect(status().isOk());

        Mockito.verify(iTaskService, Mockito.times(1)).deleteTask("1");

        System.out.println("testDeleteTask completed successfully");
    }
}
