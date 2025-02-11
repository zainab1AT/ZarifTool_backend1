package com.project.physio_backend.Services.Physiotherapist;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.project.physio_backend.Entities.Physiotherapists.DayOfWeek;
import com.project.physio_backend.Entities.Physiotherapists.Physiotherapist;
import com.project.physio_backend.Entities.Physiotherapists.WorkingHours;
import com.project.physio_backend.Entities.Users.Location;


public interface PhysiotherapistService {
    
    public ResponseEntity<Physiotherapist> addPhysiotherapist (String clicncName, long phonenumber, double price, String address, String addressLink, Location location, List<WorkingHours> workingHours);

    public ResponseEntity<?> deletePhysiotherapist (long physiotherapistID);

    public ResponseEntity<Physiotherapist> getPhysiotherapist (long physiotherapistID);

    public ResponseEntity<Physiotherapist> updatePhysiotherapist (long physiotherapistID, String clicncName, long phonenumber, double price, String address, String addressLink, Location location);

    public List<Physiotherapist> getAllPhysiotherapistsforInCity (Location location);

    public ResponseEntity<WorkingHours> addWorkingHoursToPhysiotherapist(long physiotherapistID, DayOfWeek dayOfWeek, String startTime, String endTime);

    public List<WorkingHours> getWorkingHoursForPhysiotherapist(long physiotherapistID);

    public ResponseEntity<?> deleteWorkingHourForPhysiotherapist (long workingHoursID);

    public ResponseEntity<?> deleteAllWorkingDaysForPhysiotherapist (long physiotherapistID);

    public ResponseEntity<WorkingHours> updateWorkingDaysForPhysiotherapist (long workingHoursID, DayOfWeek dayOfWeek,
    String startTime, String endTime);

}
