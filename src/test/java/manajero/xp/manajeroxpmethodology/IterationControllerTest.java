package manajero.xp.manajeroxpmethodology;

import manajero.xp.manajeroxpmethodology.Controller.IterationController;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Iteration;
import manajero.xp.manajeroxpmethodology.Services.IIterationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IterationController.class)
public class IterationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IIterationService iIterationService;

    private Iteration iteration;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        iteration = new Iteration();
        iteration.setId("1");
        iteration.setTitle("Test Iteration");
        iteration.setDescription("Test Description");
        iteration.setStartDate(LocalDateTime.now());
        iteration.setEndDate(LocalDateTime.now().plusDays(1));
    }

    @Test
    public void testCreateIteration() throws Exception {
        System.out.println("Running testCreateIteration");

        Mockito.when(iIterationService.addIteration(Mockito.any(Iteration.class))).thenReturn(iteration);

        mockMvc.perform(post("/api/iteration/addIteration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(iteration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Iteration"));

        System.out.println("testCreateIteration completed successfully");
    }

    @Test
    public void testGetIterationById() throws Exception {
        System.out.println("Running testGetIterationById");

        Mockito.when(iIterationService.getIterationById("1")).thenReturn(iteration);

        mockMvc.perform(get("/api/iteration/getIterationById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Iteration"));

        System.out.println("testGetIterationById completed successfully");
    }

    @Test
    public void testUpdateIteration() throws Exception {
        System.out.println("Running testUpdateIteration");

        Mockito.when(iIterationService.updateIteration(Mockito.any(Iteration.class))).thenReturn(iteration);

        mockMvc.perform(put("/api/iteration/updateIteration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(iteration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Iteration"));

        System.out.println("testUpdateIteration completed successfully");
    }

    @Test
    public void testDeleteIteration() throws Exception {
        System.out.println("Running testDeleteIteration");

        mockMvc.perform(delete("/api/iteration/deleteIteration/1"))
                .andExpect(status().isOk());

        Mockito.verify(iIterationService, Mockito.times(1)).deleteIteration("1");

        System.out.println("testDeleteIteration completed successfully");
    }
}
