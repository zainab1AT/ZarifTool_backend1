package com.project.physio_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.physio_backend.Controllers.UserController;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Repositories.UserRepository;
import com.project.physio_backend.Services.UserService.UserService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import io.restassured.http.ContentType;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService; 

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private User newUser = new User("john_doe", "Doe123");

    private static String token;

    @BeforeAll
    public static void setup() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"TestUser\",\"password\":\"test123\"}")
                .when()
                .post("http://localhost:8080/api/auth/signin")
                .then()
                .extract()
                .response();

        token = response.jsonPath().getString("accessToken");
        assertNotNull(token);
    }

    @Test
    void testGetAllUsers() throws Exception {

        this.mockMvc.perform(get("/api/users").header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testNewUserSuccessful() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(newUser); 
    
        mockMvc.perform(post("/api/users")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(newUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    
        verify(userService, times(1)).createUser(any(User.class)); 
    }

    @Test
    void testGetUserById() throws Exception {
        User mockUser = new User("john_doe", "Doe123");
        mockUser.setUserID(8L);
    
        when(userService.getUserById(8L)).thenReturn(Optional.of(mockUser));
        this.mockMvc.perform(get("/api/users/8").header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
              
    }

    @Test
    public void testDeleteUserSuccessful() throws Exception {
        Long userId = 7L;
        newUser.setUserID(userId);
        Long user1Id = newUser.getUserID();
        when(userRepository.findById(user1Id)).thenReturn(Optional.of(newUser));

        mockMvc.perform(
                delete("/api/users/{id}", newUser.getUserID().toString()).header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent()); 
        this.mockMvc
                .perform(get("/api/users/" + newUser.getUserID().toString()).header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testUpdateUser() throws Exception {

        User user = new User("newUser", "neSwP!ssw8ord");
        user.setUserID(1L);

        User updatedUser = new User("mk",  "new!passworK.dd5");
        updatedUser.setUserID(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/1").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
