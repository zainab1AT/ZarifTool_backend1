package com.project.physio_backend;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.physio_backend.Controllers.ProfileController;
import com.project.physio_backend.Entities.Users.Gender;
import com.project.physio_backend.Entities.Users.Location;
import com.project.physio_backend.Entities.Users.Profile;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Repositories.ProfileRepository;
import com.project.physio_backend.Repositories.UserRepository;
import com.project.physio_backend.Services.ProfileService.ProfileService;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @MockBean
    private ProfileService profileService; 

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileController profileController;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

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
void testGetProfileByIdSuccessful() throws Exception {
    User mockUser = new User("john", "Doe123");
    Profile mockProfile = new Profile("Bio", "profilePicUri", 1.75, 70, new Date(), Gender.MALE, Location.BETHLEHEM, mockUser);
    mockProfile.setProfileID(22L);

    when(profileService.getProfileById(22L)).thenReturn(Optional.of(mockProfile));

    mockMvc.perform(get("/api/profiles/{id}", 22L)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.bio").value("Bio"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.height").value(1.75))
            .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("MALE"));

    verify(profileService, times(1)).getProfileById(22L);
}

@Test
void testUpdateProfileSuccessful() throws Exception {
    User mockUser = new User("john", "Doe123");
    
    Profile existingProfile = new Profile("Old Bio", "oldPicUri", 1.70, 65, new Date(), Gender.MALE, Location.BETHLEHEM, mockUser);
    existingProfile.setProfileID(22L);

    Profile updatedProfile = new Profile("Updated Bio", "newPicUri", 1.75, 70, new Date(), Gender.MALE, Location.JERUSALEM, mockUser);
    updatedProfile.setProfileID(22L);

    when(profileService.updateProfile(eq(22L), any(Profile.class))).thenReturn(updatedProfile);

    mockMvc.perform(put("/api/profiles/{id}", 22L)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedProfile)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.bio").value("Updated Bio"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.profilePictureUri").value("newPicUri"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.height").value(1.75))
            .andExpect(MockMvcResultMatchers.jsonPath("$.weight").value(70))
            .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("JERUSALEM"));

    verify(profileService, times(1)).updateProfile(eq(22L), any(Profile.class));
}

}