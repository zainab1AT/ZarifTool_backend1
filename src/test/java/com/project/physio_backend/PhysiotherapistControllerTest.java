package com.project.physio_backend;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static io.restassured.RestAssured.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.physio_backend.Controllers.ProfileController;
import com.project.physio_backend.Entities.Physiotherapists.DayOfWeek;
import com.project.physio_backend.Entities.Physiotherapists.Physiotherapist;
import com.project.physio_backend.Entities.Physiotherapists.WorkingHours;
import com.project.physio_backend.Entities.Users.Location;
import com.project.physio_backend.Repositories.ProfileRepository;
import com.project.physio_backend.Repositories.UserRepository;
import com.project.physio_backend.Services.Physiotherapist.PhysiotherapistService;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest
@AutoConfigureMockMvc
public class PhysiotherapistControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Mock
        private UserRepository userRepository;

        @MockBean
        private PhysiotherapistService physiotherapistService;

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
        void testGetAllPhysiotherapists() throws Exception {
                List<Physiotherapist> physiotherapists = List.of(
                                new Physiotherapist("Clinic A", 123456789L, 100.0, "Address A", "Link A",
                                                Location.BETHLEHEM),
                                new Physiotherapist("Clinic B", 987654321L, 150.0, "Address B", "Link B",
                                                Location.JERUSALEM));

                when(physiotherapistService.getAllPhysiotherapists()).thenReturn(physiotherapists);

                mockMvc.perform(get("/api/physiotherapists")
                                .header("Authorization", "Bearer " + token))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].clinicName").value("Clinic A"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[1].clinicName").value("Clinic B"));

                verify(physiotherapistService, times(1)).getAllPhysiotherapists();
        }

        @Test
        void testAddPhysiotherapist() throws Exception {
                Physiotherapist physiotherapist = new Physiotherapist();
                physiotherapist.setClinicName("Test Clinic");
                physiotherapist.setPhonenumber(123456789L);
                physiotherapist.setPrice(100.0);

                when(physiotherapistService.addPhysiotherapist(
                                eq("Test Clinic"),
                                anyLong(),
                                anyDouble(),
                                any(),
                                any(),
                                any()))
                                .thenReturn(ResponseEntity.ok(physiotherapist));

                mockMvc.perform(post("/api/physiotherapists/add")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(physiotherapist)))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                verify(physiotherapistService, times(1))
                                .addPhysiotherapist(eq("Test Clinic"), anyLong(), anyDouble(), any(), any(), any());
        }

        @Test
        void testDeletePhysiotherapist() throws Exception {
                long id = 1L;

                when(physiotherapistService.deletePhysiotherapist(id))
                                .thenReturn(ResponseEntity.ok().build());

                mockMvc.perform(delete("/api/physiotherapists/delete/{id}", id)
                                .header("Authorization", "Bearer " + token))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                verify(physiotherapistService, times(1)).deletePhysiotherapist(id);
        }

        @Test
        void testGetPhysiotherapist() throws Exception {
                long id = 1L;
                Physiotherapist physiotherapist = new Physiotherapist("Test Clinic", 123456789L, 100.0, "Test Address",
                                "Test Link", Location.BETHLEHEM);

                when(physiotherapistService.getPhysiotherapist(id))
                                .thenReturn(ResponseEntity.ok(physiotherapist));

                mockMvc.perform(get("/api/physiotherapists/{id}", id)
                                .header("Authorization", "Bearer " + token))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.clinicName").value("Test Clinic"));

                verify(physiotherapistService, times(1)).getPhysiotherapist(id);
        }

        @Test
        void testUpdatePhysiotherapist() throws Exception {
                long id = 1L;
                Physiotherapist updatedPhysiotherapist = new Physiotherapist("Updated Clinic", 987654321L, 200.0,
                                "Updated Address", "Updated Link", Location.BETHLEHEM);

                when(physiotherapistService.updatePhysiotherapist(
                                eq(id),
                                eq(updatedPhysiotherapist.getClinicName()),
                                anyLong(),
                                anyDouble(),
                                any(),
                                any(),
                                any()))
                                .thenReturn(ResponseEntity.ok(updatedPhysiotherapist));

                mockMvc.perform(put("/api/physiotherapists/update/{id}", id)
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedPhysiotherapist)))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.clinicName").value("Updated Clinic"));

                verify(physiotherapistService, times(1)).updatePhysiotherapist(
                                eq(id),
                                eq(updatedPhysiotherapist.getClinicName()),
                                anyLong(),
                                anyDouble(),
                                any(),
                                any(),
                                any());
        }

        @Test
        void testGetPhysiotherapistsByCity() throws Exception {
                when(physiotherapistService.getAllPhysiotherapistsforInCity(any()))
                                .thenReturn(List.of(new Physiotherapist()));

                mockMvc.perform(get("/api/physiotherapists/city/BETHLEHEM")
                                .header("Authorization", "Bearer " + token))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void testAddWorkingHours() throws Exception {
                WorkingHours workingHours = new WorkingHours(DayOfWeek.FRIDAY, "09:00", "17:00");
                when(physiotherapistService.addWorkingHoursToPhysiotherapist(anyLong(), any(), any(), any()))
                                .thenReturn(ResponseEntity.ok(workingHours));

                mockMvc.perform(post("/api/physiotherapists/1/working-hours/add")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(workingHours)))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void testGetWorkingHoursForTherapist() throws Exception {
                when(physiotherapistService.getWorkingHoursForPhysiotherapist(1L))
                                .thenReturn(List.of(new WorkingHours(DayOfWeek.FRIDAY, "09:00", "17:00")));

                mockMvc.perform(get("/api/physiotherapists/1/working-hours")
                                .header("Authorization", "Bearer " + token))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void testDeleteWorkingHour() throws Exception {
                when(physiotherapistService.deleteWorkingHourForPhysiotherapist(1L))
                                .thenReturn(ResponseEntity.ok().build());

                mockMvc.perform(delete("/api/physiotherapists/working-hours/1/delete")
                                .header("Authorization", "Bearer " + token))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void testDeleteAllWorkingHours() throws Exception {
                when(physiotherapistService.deleteAllWorkingDaysForPhysiotherapist(1L))
                                .thenReturn(ResponseEntity.ok().build());

                mockMvc.perform(delete("/api/physiotherapists/1/working-hours/delete-all")
                                .header("Authorization", "Bearer " + token))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void testUpdateWorkingHours() throws Exception {
                WorkingHours updatedHours = new WorkingHours(DayOfWeek.FRIDAY, "10:00", "18:00");
                when(physiotherapistService.updateWorkingDaysForPhysiotherapist(anyLong(), any(), any(), any()))
                                .thenReturn(ResponseEntity.ok(updatedHours));

                mockMvc.perform(put("/api/physiotherapists/working-hours/1/update")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedHours)))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

}
