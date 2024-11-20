package manajero.xp.manajeroxpmethodology;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Status;
import manajero.xp.manajeroxpmethodology.Entities.manajero.UserStory;
import manajero.xp.manajeroxpmethodology.Repositories.UserStoryRepository;
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
public class UserStoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserStoryRepository userStoryRepository;

    private UserStory userStory;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        userStory = UserStory.builder()
                .id("1")
                .title("Test UserStory")
                .description("Test UserStory Description")
                .priority("High")
                .status(Status.TO_DO)
                .build();

        userStoryRepository.save(userStory);
    }

    @Test
    public void testCreateUserStory() throws Exception {
        UserStory newUserStory = UserStory.builder()
                .id("2")
                .title("New UserStory")
                .description("New UserStory Description")
                .priority("Medium")
                .status(Status.IN_PROGRESS)
                .build();

        mockMvc.perform(post("/api/userStory/addUserStory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserStory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.title").value("New UserStory"));

        System.out.println("Integration test for create user story completed successfully");
    }

    @Test
    public void testGetUserStoryById() throws Exception {
        mockMvc.perform(get("/api/userStory/getUserStoryById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test UserStory"));

        System.out.println("Integration test for get user story by id completed successfully");
    }

    @Test
    public void testUpdateUserStory() throws Exception {
        userStory.setTitle("Updated UserStory Title");

        mockMvc.perform(put("/api/userStory/updateUserStory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userStory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Updated UserStory Title"));

        System.out.println("Integration test for update user story completed successfully");
    }

    @Test
    public void testDeleteUserStory() throws Exception {
        mockMvc.perform(delete("/api/userStory/deleteUserStory/1"))
                .andExpect(status().isOk());

        System.out.println("Integration test for delete user story completed successfully");
    }
}
