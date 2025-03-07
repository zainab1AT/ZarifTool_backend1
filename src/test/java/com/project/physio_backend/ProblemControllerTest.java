package com.project.physio_backend;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static io.restassured.RestAssured.given;

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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.physio_backend.Controllers.ProblemController;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Repositories.ProblemRepository;
import com.project.physio_backend.Repositories.UserRepository;
import com.project.physio_backend.Services.ProblemService.ProblemService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest
@AutoConfigureMockMvc
public class ProblemControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ProblemService problemService;

        @Mock
        private UserRepository userRepository;

        @Mock
        private ProblemRepository problemRepository;

        @InjectMocks
        private ProblemController problemController;

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
        void testGetAllProblems() throws Exception {
                Problem problem1 = new Problem("Problem1", "Description1");
                Problem problem2 = new Problem("Problem2", "Description2");
                List<Problem> problems = Arrays.asList(problem1, problem2);

                when(problemService.getAllProblems()).thenReturn(problems);

                mockMvc.perform(get("/api/problems")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].name").value("Problem1"))
                                .andExpect(jsonPath("$[1].name").value("Problem2"));

                verify(problemService, times(1)).getAllProblems();
        }

        @Test
        void testGetProblemById() throws Exception {
                Problem problem = new Problem("Problem1", "Description1");
                problem.setProblemID(1L);

                when(problemService.getProblemById(1L)).thenReturn(problem);

                mockMvc.perform(get("/api/problems/{id}", 1L)
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Problem1"))
                                .andExpect(jsonPath("$.description").value("Description1"));

                verify(problemService, times(1)).getProblemById(1L);
        }

        @Test
        void testCreateProblem() throws Exception {
                Problem problem = new Problem("Problem1", "Description1");

                when(problemService.createProblem(any(Problem.class))).thenReturn(problem);

                mockMvc.perform(post("/api/problems")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(problem)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Problem1"))
                                .andExpect(jsonPath("$.description").value("Description1"));

                verify(problemService, times(1)).createProblem(any(Problem.class));
        }

        @Test
        void testCreateProblemWithImage() throws Exception {
                Problem problem = new Problem("Problem1", "Description1");
                problem.setProblemID(1L);

                MockMultipartFile file = new MockMultipartFile(
                                "image",
                                "test.jpg",
                                MediaType.IMAGE_JPEG_VALUE,
                                "test image content".getBytes());

                MockMultipartFile problemJson = new MockMultipartFile(
                                "problem",
                                "",
                                MediaType.APPLICATION_JSON_VALUE,
                                objectMapper.writeValueAsBytes(problem));

                when(problemService.createProblemWithImage(any(Problem.class), any(MultipartFile.class)))
                                .thenReturn(problem);

                mockMvc.perform(MockMvcRequestBuilders.multipart("/api/problems/image")
                                .file(file)
                                .file(problemJson)
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Problem1"))
                                .andExpect(jsonPath("$.description").value("Description1"));

                verify(problemService, times(1)).createProblemWithImage(any(Problem.class), any(MultipartFile.class));
        }

        @Test
        void testUpdateProblem() throws Exception {
                Problem existingProblem = new Problem("Problem1", "Description1");
                existingProblem.setProblemID(1L);

                Problem updatedProblem = new Problem("UpdatedProblem", "UpdatedDescription");

                MockMultipartFile file = new MockMultipartFile(
                                "image",
                                "test.jpg",
                                MediaType.IMAGE_JPEG_VALUE,
                                "test image content".getBytes());

                MockMultipartFile problemJson = new MockMultipartFile(
                                "problem",
                                "",
                                MediaType.APPLICATION_JSON_VALUE,
                                objectMapper.writeValueAsBytes(updatedProblem));

                when(problemService.updateProblem(eq(1L), any(Problem.class), any(MultipartFile.class)))
                                .thenReturn(updatedProblem);

                mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/api/problems/{id}", 1L)
                                .file(problemJson)
                                .file(file)
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("UpdatedProblem"))
                                .andExpect(jsonPath("$.description").value("UpdatedDescription"));

                verify(problemService, times(1)).updateProblem(eq(1L), any(Problem.class), any(MultipartFile.class));
        }

        @Test
        void testDeleteProblem() throws Exception {
                doNothing().when(problemService).deleteProblem(1L);

                mockMvc.perform(delete("/api/problems/{id}", 1L)
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());

                verify(problemService, times(1)).deleteProblem(1L);
        }

        @Test
        void testAddProblemToUser() throws Exception {
                User user = new User("john", "Doe123");
                user.setUserID(1L);

                when(problemService.addProblemToUser(1L, 1L)).thenReturn(user);

                mockMvc.perform(post("/api/problems/user/{userId}/add-problem/{problemId}", 1L, 1L)
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.username").value("john"));

                verify(problemService, times(1)).addProblemToUser(1L, 1L);
        }

        @Test
        void testRemoveProblemFromUser() throws Exception {
                User user = new User("john", "Doe123");
                user.setUserID(1L);

                when(problemService.removeProblemFromUser(1L, 1L)).thenReturn(user);

                mockMvc.perform(delete("/api/problems/user/{userId}/remove-problem/{problemId}", 1L, 1L)
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.username").value("john"));

                verify(problemService, times(1)).removeProblemFromUser(1L, 1L);
        }

        @Test
        void testGetUserProblems() throws Exception {
                Problem problem1 = new Problem("Problem1", "Description1");
                Problem problem2 = new Problem("Problem2", "Description2");
                List<Problem> problems = Arrays.asList(problem1, problem2);

                when(problemService.getUserProblems(1L)).thenReturn(problems);

                mockMvc.perform(get("/api/problems/{id}/problems", 1L)
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].name").value("Problem1"))
                                .andExpect(jsonPath("$[1].name").value("Problem2"));

                verify(problemService, times(1)).getUserProblems(1L);
        }
}