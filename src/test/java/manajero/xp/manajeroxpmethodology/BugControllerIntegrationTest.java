package manajero.xp.manajeroxpmethodology;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Bug;
import manajero.xp.manajeroxpmethodology.Entities.manajero.BugStatus;
import manajero.xp.manajeroxpmethodology.Repositories.BugRepository;
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
public class BugControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BugRepository bugRepository;

    private Bug bug;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        bug = Bug.builder()
                .id("1")
                .title("Test Bug")
                .description("Test Bug Description")
                .severity("High")
                .status(BugStatus.OPEN)
                .build();

        bugRepository.save(bug);
    }

    @Test
    public void testCreateBug() throws Exception {
        Bug newBug = Bug.builder()
                .id("2")
                .title("New Bug")
                .description("New Bug Description")
                .severity("Medium")
                .status(BugStatus.OPEN)
                .build();

        mockMvc.perform(post("/api/bug/addBug")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBug)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.title").value("New Bug"));

        System.out.println("Integration test for create bug completed successfully");
    }

    @Test
    public void testGetBugById() throws Exception {
        mockMvc.perform(get("/api/bug/getBugById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Bug"));

        System.out.println("Integration test for get bug by id completed successfully");
    }

    @Test
    public void testUpdateBug() throws Exception {
        bug.setTitle("Updated Bug Title");

        mockMvc.perform(put("/api/bug/updateBug")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bug)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Updated Bug Title"));

        System.out.println("Integration test for update bug completed successfully");
    }

    @Test
    public void testDeleteBug() throws Exception {
        mockMvc.perform(delete("/api/bug/deleteBug/1"))
                .andExpect(status().isOk());

        System.out.println("Integration test for delete bug completed successfully");
    }
}
