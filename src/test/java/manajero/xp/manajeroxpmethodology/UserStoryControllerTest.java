package manajero.xp.manajeroxpmethodology;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import manajero.xp.manajeroxpmethodology.Controller.UserStoryController;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Status;
import manajero.xp.manajeroxpmethodology.Entities.manajero.UserStory;
import manajero.xp.manajeroxpmethodology.Services.IUserStoryService;
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
@WebMvcTest(UserStoryController.class)
public class UserStoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserStoryService iUserStoryService;

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
    }

    @Test
    public void testCreateUserStory() throws Exception {
        Mockito.when(iUserStoryService.addUserStory(Mockito.any(UserStory.class))).thenReturn(userStory);

        mockMvc.perform(post("/api/userStory/addUserStory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userStory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test UserStory"));

        System.out.println("testCreateUserStory completed successfully");
    }

    @Test
    public void testGetUserStoryById() throws Exception {
        Mockito.when(iUserStoryService.getUserStoryById("1")).thenReturn(userStory);

        mockMvc.perform(get("/api/userStory/getUserStoryById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test UserStory"));

        System.out.println("testGetUserStoryById completed successfully");
    }

    @Test
    public void testUpdateUserStory() throws Exception {
        Mockito.when(iUserStoryService.updateUserStory(Mockito.any(UserStory.class))).thenReturn(userStory);

        mockMvc.perform(put("/api/userStory/updateUserStory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userStory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test UserStory"));

        System.out.println("testUpdateUserStory completed successfully");
    }

    @Test
    public void testDeleteUserStory() throws Exception {
        mockMvc.perform(delete("/api/userStory/deleteUserStory/1"))
                .andExpect(status().isOk());

        Mockito.verify(iUserStoryService, Mockito.times(1)).deleteUserStory("1");

        System.out.println("testDeleteUserStory completed successfully");
    }
}

