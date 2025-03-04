// package com.project.physio_backend;

// import static io.restassured.RestAssured.given;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import java.time.LocalDateTime;
// import java.util.Arrays;
// import java.util.List;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
// import com.project.physio_backend.Controllers.ProgressController;
// import com.project.physio_backend.Entities.Progress.Progress;
// import com.project.physio_backend.Repositories.ProblemRepository;
// import com.project.physio_backend.Repositories.ProgressRepository;
// import com.project.physio_backend.Services.ProgressService.ProgressService;

// import io.restassured.http.ContentType;
// import io.restassured.response.Response;

// @SpringBootTest
// @AutoConfigureMockMvc
// public class ProgressControllerTest {

//   @Autowired
//   private MockMvc mockMvc;

//   @MockBean
//   private ProgressService progressService;

//   @Mock
//   private ProblemRepository problemRepository;

//   @Mock
//   private ProgressRepository progressRepository;
//   @InjectMocks
//   private ProgressController progressController;

//   private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

//   private static String token;

//   @BeforeAll
//   public static void setup() {
//     Response response = given()
//         .contentType(ContentType.JSON)
//         .body("{\"username\":\"nour22\",\"password\":\"nour2003\"}")
//         .when()
//         .post("http://localhost:8080/api/auth/signin")
//         .then()
//         .extract()
//         .response();

//     token = response.jsonPath().getString("accessToken");
//     assertNotNull(token);
//   }

//   @Test
//   void testGetAllProgresses() throws Exception {
//     Progress progress1 = new Progress(LocalDateTime.now(), 50.0);
//     Progress progress2 = new Progress(LocalDateTime.now(), 75.0);
//     List<Progress> progresses = Arrays.asList(progress1, progress2);

//     when(progressService.getAllProgresses()).thenReturn(progresses);

//     mockMvc.perform(get("/api/progresses")
//         .header("Authorization", "Bearer " + token)
//         .contentType(MediaType.APPLICATION_JSON))
//         .andExpect(status().isOk())
//         .andExpect(jsonPath("$[0].percentag").value(50.0))
//         .andExpect(jsonPath("$[1].percentag").value(75.0));

//     verify(progressService, times(1)).getAllProgresses();
//   }

//   @Test
//   void testGetProgressById() throws Exception {
//     Progress progress = new Progress(LocalDateTime.now(), 50.0);
//     progress.setProgressID(1L);

//     when(progressService.getProgressById(1L)).thenReturn(progress);

//     mockMvc.perform(get("/api/progresses/{id}", 1L)
//         .header("Authorization", "Bearer " + token)
//         .contentType(MediaType.APPLICATION_JSON))
//         .andExpect(status().isOk())
//         .andExpect(jsonPath("$.percentag").value(50.0));

//     verify(progressService, times(1)).getProgressById(1L);
//   }

//   @Test
//   void testCreateProgress() throws Exception {
//     Progress progress = new Progress(LocalDateTime.now(), 50.0);
//     progress.setProgressID(1L);

//     when(progressService.createProgress(any(Progress.class))).thenReturn(progress);

//     mockMvc.perform(post("/api/progresses")
//         .header("Authorization", "Bearer " + token)
//         .contentType(MediaType.APPLICATION_JSON)
//         .content(objectMapper.writeValueAsString(progress)))
//         .andExpect(status().isCreated())
//         .andExpect(jsonPath("$.percentag").value(50.0));

//     verify(progressService, times(1)).createProgress(any(Progress.class));
//   }

//   @Test
//   void testUpdateProgress() throws Exception {
//     Progress existingProgress = new Progress(LocalDateTime.now(), 50.0);
//     existingProgress.setProgressID(1L);

//     Progress updatedProgress = new Progress(LocalDateTime.now(), 75.0);
//     updatedProgress.setProgressID(1L);

//     when(progressService.updateProgress(eq(1L), any(Progress.class))).thenReturn(updatedProgress);

//     mockMvc.perform(put("/api/progresses/{id}", 1L)
//         .header("Authorization", "Bearer " + token)
//         .contentType(MediaType.APPLICATION_JSON)
//         .content(objectMapper.writeValueAsString(updatedProgress)))
//         .andExpect(status().isOk())
//         .andExpect(jsonPath("$.percentag").value(75.0));

//     verify(progressService, times(1)).updateProgress(eq(1L), any(Progress.class));
//   }

//   @Test
//   void testDeleteProgress() throws Exception {
//     doNothing().when(progressService).deleteProgress(1L);

//     mockMvc.perform(delete("/api/progresses/{id}", 1L)
//         .header("Authorization", "Bearer " + token)
//         .contentType(MediaType.APPLICATION_JSON))
//         .andExpect(status().isNoContent());

//     verify(progressService, times(1)).deleteProgress(1L);
//   }
// }