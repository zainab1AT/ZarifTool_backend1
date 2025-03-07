package com.project.physio_backend.Services.Physiotherapist;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.physio_backend.Entities.Physiotherapists.DayOfWeek;
import com.project.physio_backend.Entities.Physiotherapists.Physiotherapist;
import com.project.physio_backend.Entities.Physiotherapists.WorkingHours;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Users.Location;
import com.project.physio_backend.Exceptions.Physiotherapists.PhysiotherapistNotFoundException;
import com.project.physio_backend.Exceptions.WorkingHours.WorkingHoursNotFoundException;
import com.project.physio_backend.Repositories.*;
import com.project.physio_backend.Services.ImageService.ImageService;

@Service
public class PhysiotherapistServiceImpl implements PhysiotherapistService {

    @Autowired
    private PhysiotherapistRepository physiotherapistRepository;

    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public List<Physiotherapist> getAllPhysiotherapists() {
        return physiotherapistRepository.findAll();
    }

    @Override
    public ResponseEntity<Physiotherapist> addPhysiotherapist(String clinicName, long phoneNumber, double price,
            String address, String addressLink, Location location) {
        Physiotherapist physiotherapist = new Physiotherapist(clinicName, phoneNumber, price, address, addressLink,
                location);

        Physiotherapist savedPhysiotherapist = physiotherapistRepository.save(physiotherapist);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPhysiotherapist);
    }

    @Override
    public ResponseEntity<Physiotherapist> addPhysiotherapistWithImage(String clinicName, long phoneNumber,
            double price,
            String address, String addressLink, Location location, MultipartFile multipartFile) {
        Physiotherapist physiotherapist = new Physiotherapist(clinicName, phoneNumber, price, address, addressLink,
                location);

        Physiotherapist savedPhysiotherapist = physiotherapistRepository.save(physiotherapist);
        imageService.uploadImageForPhysiotherapist(multipartFile, physiotherapist.getPhysiotherapistID());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPhysiotherapist);
    }

    @Override
    public ResponseEntity<?> deletePhysiotherapist(long physiotherapistID) {
        physiotherapistRepository.deleteById(physiotherapistID);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Physiotherapist> getPhysiotherapist(long physiotherapistID) {
        return physiotherapistRepository.findById(physiotherapistID)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PhysiotherapistNotFoundException(physiotherapistID));
    }

    @Override
    public ResponseEntity<Physiotherapist> updatePhysiotherapist(long physiotherapistID, String clinicName,
            long phoneNumber, double price, String address, String addressLink, Location location) {
        Physiotherapist physiotherapist = physiotherapistRepository.findById(physiotherapistID)
                .orElseThrow(() -> new PhysiotherapistNotFoundException(physiotherapistID));

        physiotherapist.setClinicName(clinicName);
        physiotherapist.setPhonenumber(phoneNumber);
        physiotherapist.setPrice(price);
        physiotherapist.setAddress(address);
        physiotherapist.setAddressLink(addressLink);
        physiotherapist.setLocation(location);
        Physiotherapist updatedPhysiotherapist = physiotherapistRepository.save(physiotherapist);
        return ResponseEntity.ok(updatedPhysiotherapist);
    }

    @Override
    public List<Physiotherapist> getAllPhysiotherapistsforInCity(Location location) {
        List<Physiotherapist> physiotherapists = physiotherapistRepository.findAll();
        List<Physiotherapist> physiotherapists2 = new ArrayList<>();
        for (int i = 0; i < physiotherapists.size(); i++) {
            if (physiotherapists.get(i).getLocation().equals(location)) {
                physiotherapists2.add(physiotherapists.get(i));
            }
        }
        return physiotherapists2;
    }

    @Override
    public ResponseEntity<WorkingHours> addWorkingHoursToPhysiotherapist(long physiotherapistID, DayOfWeek dayOfWeek,
            String startTime, String endTime) {
        Physiotherapist physiotherapist = physiotherapistRepository.findById(physiotherapistID)
                .orElseThrow(() -> new PhysiotherapistNotFoundException(physiotherapistID));

        WorkingHours workingHour = new WorkingHours();
        workingHour.setPhysiotherapist(physiotherapist);
        workingHour.setDayOfWeek(dayOfWeek);
        workingHour.setStartTime(startTime);
        workingHour.setEndTime(endTime);

        WorkingHours savedWorkingHour = workingHoursRepository.save(workingHour);
        return ResponseEntity.ok(savedWorkingHour);
    }

    @Override
    public List<WorkingHours> getWorkingHoursForPhysiotherapist(long physiotherapistID) {
        Physiotherapist physiotherapist = physiotherapistRepository.findById(physiotherapistID)
                .orElseThrow(() -> new PhysiotherapistNotFoundException(physiotherapistID));
        List<WorkingHours> workingHours = physiotherapist.getWorkingHours();
        return workingHours;
    }

    @Override
    public ResponseEntity<?> deleteWorkingHourForPhysiotherapist(long workingHoursID) {
        workingHoursRepository.deleteById(workingHoursID);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteAllWorkingDaysForPhysiotherapist(long physiotherapistID) {
        Physiotherapist physiotherapist = physiotherapistRepository.findById(physiotherapistID)
                .orElseThrow(() -> new PhysiotherapistNotFoundException(physiotherapistID));

        List<WorkingHours> workingHours = physiotherapist.getWorkingHours();
        for (WorkingHours workingHour : workingHours) {
            workingHoursRepository.deleteById(workingHour.getWorkingHoursID());
        }

        // Clear the list and save the physiotherapist entity
        physiotherapist.getWorkingHours().clear();
        physiotherapistRepository.save(physiotherapist); // Save the changes

        return ResponseEntity.ok("All working days deleted successfully");
    }

    @Override
    public ResponseEntity<WorkingHours> updateWorkingDaysForPhysiotherapist(long workingHoursID, DayOfWeek dayOfWeek,
            String startTime, String endTime) {
        WorkingHours workingHour = workingHoursRepository.findById(workingHoursID)
                .orElseThrow(() -> new WorkingHoursNotFoundException(workingHoursID));
        workingHour.setDayOfWeek(dayOfWeek);
        workingHour.setStartTime(startTime);
        workingHour.setEndTime(endTime);
        return ResponseEntity.ok(workingHoursRepository.save(workingHour));
    }
}