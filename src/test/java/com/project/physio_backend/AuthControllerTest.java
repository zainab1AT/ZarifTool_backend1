package com.project.physio_backend;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Repositories.UserRepository;
import com.project.physio_backend.Security.jwt.JwtUtils;
import com.project.physio_backend.Security.services.UserDetailsImpl;
import com.project.physio_backend.payload.request.SignupRequest;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    private static String token;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

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
    void authenticateUserReturnsToken() throws Exception {

        Authentication authentication = Mockito.mock(Authentication.class);
        UserDetailsImpl userDetails = Mockito.mock(UserDetailsImpl.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(any())).thenReturn(token);

        ResultActions result = mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"username\", \"password\": \"password\" }"));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void registerUserSuccessful() throws Exception {
        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder encoderPass = mock(PasswordEncoder.class);

        User user = new User("newUser1", "newuser123");

        SignupRequest signupRequest = new SignupRequest(user.getUsername(), user.getPassword());

        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        when(encoderPass.encode(signupRequest.getPassword())).thenReturn("encodedPassword");

        ResultActions result = mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"newUser1\",\"password\":\"newuser123\"}}"));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void registerUserUsernameAlreadyExists() throws Exception {

        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder encoderPass = mock(PasswordEncoder.class);

        User user = new User("NewUser", "newuser123");

        SignupRequest signupRequest = new SignupRequest(user.getUsername(), user.getPassword());

        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        ResultActions result = mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"NewUser\",\"password\":\"newuser123\"}}"));

        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}