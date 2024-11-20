package manajero.xp.manajeroxpmethodology;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import manajero.xp.manajeroxpmethodology.Controller.BugController;
import manajero.xp.manajeroxpmethodology.Entities.manajero.Bug;
import manajero.xp.manajeroxpmethodology.Entities.manajero.BugStatus;
import manajero.xp.manajeroxpmethodology.Services.IBugService;
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

@WebMvcTest(BugController.class)
public class BugControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBugService iBugService;

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
    }

    @Test
    public void testCreateBug() throws Exception {
        Mockito.when(iBugService.addBug(Mockito.any(Bug.class))).thenReturn(bug);

        mockMvc.perform(post("/api/bug/addBug")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bug)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Bug"));

        System.out.println("testCreateBug completed successfully");
    }

    @Test
    public void testGetBugById() throws Exception {
        Mockito.when(iBugService.getBugById("1")).thenReturn(bug);

        mockMvc.perform(get("/api/bug/getBugById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Bug"));

        System.out.println("testGetBugById completed successfully");
    }

    @Test
    public void testUpdateBug() throws Exception {
        Mockito.when(iBugService.updateBug(Mockito.any(Bug.class))).thenReturn(bug);

        mockMvc.perform(put("/api/bug/updateBug")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bug)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Bug"));

        System.out.println("testUpdateBug completed successfully");
    }

    @Test
    public void testDeleteBug() throws Exception {
        mockMvc.perform(delete("/api/bug/deleteBug/1"))
                .andExpect(status().isOk());

        Mockito.verify(iBugService, Mockito.times(1)).deleteBug("1");

        System.out.println("testDeleteBug completed successfully");
    }
}
