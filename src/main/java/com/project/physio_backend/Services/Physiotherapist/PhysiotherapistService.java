package com.project.physio_backend.Services.Physiotherapist;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.project.physio_backend.Entities.Physiotherapists.DayOfWeek;
import com.project.physio_backend.Entities.Physiotherapists.Physiotherapist;
import com.project.physio_backend.Entities.Physiotherapists.WorkingHours;
import com.project.physio_backend.Entities.Users.Location;

import io.jsonwebtoken.io.IOException;

public interface PhysiotherapistService {
        List<Physiotherapist> getAllPhysiotherapists();

        public ResponseEntity<Physiotherapist> addPhysiotherapist(String clicncName, long phonenumber, double price,
                        String address, String addressLink, Location location);

        public ResponseEntity<Physiotherapist> addPhysiotherapistWithImage(String clicncName, long phonenumber,
                        double price, String address, String addressLink, Location location,
                        MultipartFile multipartFile)
                        throws IOException;

        public ResponseEntity<?> deletePhysiotherapist(long physiotherapistID);

        public ResponseEntity<Physiotherapist> getPhysiotherapist(long physiotherapistID);

        public ResponseEntity<Physiotherapist> updatePhysiotherapist(long physiotherapistID, String clicncName,
                        long phonenumber, double price, String address, String addressLink, Location location);

        public List<Physiotherapist> getAllPhysiotherapistsforInCity(Location location);

        public ResponseEntity<WorkingHours> addWorkingHoursToPhysiotherapist(long physiotherapistID,
                        DayOfWeek dayOfWeek,
                        String startTime, String endTime);

        public List<WorkingHours> getWorkingHoursForPhysiotherapist(long physiotherapistID);

        public ResponseEntity<?> deleteWorkingHourForPhysiotherapist(long workingHoursID);

        public ResponseEntity<?> deleteAllWorkingDaysForPhysiotherapist(long physiotherapistID);

        public ResponseEntity<WorkingHours> updateWorkingDaysForPhysiotherapist(long workingHoursID,
                        DayOfWeek dayOfWeek,
                        String startTime, String endTime);

        public List<Physiotherapist> getPhysiotherapitsWithSameLocationAsUser(Long userID);

}
