package com.project.physio_backend;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.physio_backend.Controllers.ExcerciseController;
import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Repositories.ExerciseRepository;
import com.project.physio_backend.Repositories.ProblemRepository;
import com.project.physio_backend.Services.Exercise.ExerciseService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest
@AutoConfigureMockMvc
public class ExcerciseControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ExerciseService exerciseService;

  @Mock
  private ExerciseRepository exerciseRepository;

  @Mock
  private ProblemRepository problemRepository;

  @InjectMocks
  private ExcerciseController excerciseController;

  private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  private static String token;

  @BeforeAll
  public static void setup() {
    Response response = given()
        .contentType(ContentType.JSON)
        .body("{\"username\":\"nour22\",\"password\":\"nour2003\"}")
        .when()
        .post("http://localhost:8080/api/auth/signin")
        .then()
        .extract()
        .response();

    token = response.jsonPath().getString("accessToken");
    assertNotNull(token);
  }

  @Test
  void testAddExercise() throws Exception {
    Exercise exercise = new Exercise();
    exercise.setExerciseDescription("Description1");
    exercise.setExerciseDuration(30);

    when(exerciseService.addExercise(eq(1L), any(String.class), any(Integer.class)))
        .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(exercise));

    mockMvc.perform(post("/api/exercises/add/{problemID}", 1L)
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(exercise)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.exerciseDescription").value("Description1"))
        .andExpect(jsonPath("$.exerciseDuration").value(30));

    verify(exerciseService, times(1)).addExercise(eq(1L), any(String.class), any(Integer.class));
  }

  @Test
  void testAddExerciseWithImage() throws Exception {
    Exercise exercise = new Exercise();
    exercise.setExerciseDescription("Description1");
    exercise.setExerciseDuration(30);

    MockMultipartFile file = new MockMultipartFile(
        "image",
        "test.jpg",
        MediaType.IMAGE_JPEG_VALUE,
        "test image content".getBytes());

    MockMultipartFile exerciseJson = new MockMultipartFile(
        "exercise",
        "",
        MediaType.APPLICATION_JSON_VALUE,
        objectMapper.writeValueAsBytes(exercise));

    when(exerciseService.addExerciseWithImage(eq(1L), any(String.class), any(Integer.class), any(MultipartFile.class)))
        .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(exercise));

    mockMvc.perform(MockMvcRequestBuilders.multipart("/api/exercises/add/{problemID}/image", 1L)
        .file(file)
        .file(exerciseJson)
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.exerciseDescription").value("Description1"))
        .andExpect(jsonPath("$.exerciseDuration").value(30));

    verify(exerciseService, times(1)).addExerciseWithImage(eq(1L), any(String.class), any(Integer.class),
        any(MultipartFile.class));
  }

  @Test
  void testDeleteExercise() throws Exception {
    when(exerciseService.deleteExercise(1L)).thenReturn(ResponseEntity.noContent().build());

    mockMvc.perform(delete("/api/exercises/delete/{exerciseID}", 1L)
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(exerciseService, times(1)).deleteExercise(1L);
  }

  @Test
  void testUpdateExercise() throws Exception {
    Exercise exercise = new Exercise();
    exercise.setExerciseDescription("UpdatedDescription");
    exercise.setExerciseDuration(45);

    when(exerciseService.updateExercise(eq(1L), any(String.class), any(Integer.class)))
        .thenReturn(ResponseEntity.status(HttpStatus.OK).body(exercise));

    mockMvc.perform(put("/api/exercises/update/{exerciseID}", 1L)
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(exercise)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.exerciseDescription").value("UpdatedDescription"))
        .andExpect(jsonPath("$.exerciseDuration").value(45));

    verify(exerciseService, times(1)).updateExercise(eq(1L), any(String.class), any(Integer.class));
  }

  @Test
  void testGetExercise() throws Exception {
    Exercise exercise = new Exercise();
    exercise.setExerciseDescription("Description1");
    exercise.setExerciseDuration(30);

    when(exerciseService.getExercise(1L)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(exercise));

    mockMvc.perform(get("/api/exercises/{exerciseID}", 1L)
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.exerciseDescription").value("Description1"))
        .andExpect(jsonPath("$.exerciseDuration").value(30));

    verify(exerciseService, times(1)).getExercise(1L);
  }

  @Test
  void testGetAllExercisesForProblem() throws Exception {
    Exercise exercise1 = new Exercise();
    exercise1.setExerciseDescription("Description1");
    exercise1.setExerciseDuration(30);

    Exercise exercise2 = new Exercise();
    exercise2.setExerciseDescription("Description2");
    exercise2.setExerciseDuration(45);

    List<Exercise> exercises = Arrays.asList(exercise1, exercise2);

    when(exerciseService.getAllExercisesforProblem(1L)).thenReturn(exercises);

    mockMvc.perform(get("/api/exercises/problem/{problemID}", 1L)
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].exerciseDescription").value("Description1"))
        .andExpect(jsonPath("$[1].exerciseDescription").value("Description2"));

    verify(exerciseService, times(1)).getAllExercisesforProblem(1L);
  }
}