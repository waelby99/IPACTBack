package manajero.xp.manajeroxpmethodology;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Iteration;
import manajero.xp.manajeroxpmethodology.Repositories.IterationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IterationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IterationRepository iterationRepository;

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

        iterationRepository.save(iteration);
    }

    @Test
    public void testCreateIteration() throws Exception {
        Iteration newIteration = new Iteration();
        newIteration.setId("2");
        newIteration.setTitle("New Iteration");
        newIteration.setDescription("New Description");
        newIteration.setStartDate(LocalDateTime.now());
        newIteration.setEndDate(LocalDateTime.now().plusDays(2));

        mockMvc.perform(post("/api/iteration/addIteration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newIteration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.title").value("New Iteration"));

        System.out.println("Integration test for create iteration completed successfully");
    }

    @Test

    public void testGetIterationById() throws Exception {
        mockMvc.perform(get("/api/iteration/getIterationById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Iteration"));

        System.out.println("Integration test for get iteration by id completed successfully");
    }

    @Test

    public void testUpdateIteration() throws Exception {
        iteration.setTitle("Updated Title");

        mockMvc.perform(put("/api/iteration/updateIteration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(iteration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Updated Title"));

        System.out.println("Integration test for update iteration completed successfully");
    }

    @Test
    public void testDeleteIteration() throws Exception {
        mockMvc.perform(delete("/api/iteration/deleteIteration/1"))
                .andExpect(status().isOk());

        System.out.println("Integration test for delete iteration completed successfully");
    }
}
