package com.project.physio_backend.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.physio_backend.Entities.Physiotherapists.Physiotherapist;
import com.project.physio_backend.Entities.Physiotherapists.WorkingHours;
import com.project.physio_backend.Entities.Users.Location;
import com.project.physio_backend.Services.Physiotherapist.PhysiotherapistService;

@RestController
@RequestMapping("/api/physiotherapists")
public class PhysiotherapistController {

    @Autowired
    private PhysiotherapistService physiotherapistService;

    @PostMapping("/add")
    public ResponseEntity<Physiotherapist> addPhysiotherapist(@RequestBody Physiotherapist physiotherapist) {
        return physiotherapistService.addPhysiotherapist(physiotherapist.getClinicName(),
                physiotherapist.getPhonenumber(), physiotherapist.getPrice(), physiotherapist.getAddress(),
                physiotherapist.getAddressLink(), physiotherapist.getLocation(), physiotherapist.getPhysiotherapitsImage());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePhysiotherapist(@PathVariable long id) {
        return physiotherapistService.deletePhysiotherapist(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Physiotherapist> getPhysiotherapist(@PathVariable long id) {
        return physiotherapistService.getPhysiotherapist(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Physiotherapist> updatePhysiotherapist(@PathVariable long id,
            @RequestBody Physiotherapist physiotherapist) {
        return physiotherapistService.updatePhysiotherapist(id, physiotherapist.getClinicName(),
                physiotherapist.getPhonenumber(), physiotherapist.getPrice(), physiotherapist.getAddress(),
                physiotherapist.getAddressLink(), physiotherapist.getLocation(), physiotherapist.getPhysiotherapitsImage());
    }

    @GetMapping("/city/{location}")
    public List<Physiotherapist> getPhysiotherapistsByCity(@PathVariable Location location) {
        return physiotherapistService.getAllPhysiotherapistsforInCity(location);
    }

    @PostMapping("/{id}/working-hours/add")
    public ResponseEntity<WorkingHours> addWorkingHours(@PathVariable long id, @RequestBody WorkingHours workingHours) {
        return physiotherapistService.addWorkingHoursToPhysiotherapist(id, workingHours.getDayOfWeek(),
                workingHours.getStartTime(), workingHours.getEndTime());
    }

    @GetMapping("/{id}/working-hours")
    public List<WorkingHours> getWorkingHoursforTherapits(@PathVariable long id) {
        return physiotherapistService.getWorkingHoursForPhysiotherapist(id);
    }

    @DeleteMapping("/working-hours/{id}/delete")
    public ResponseEntity<?> deleteWorkingHour(@PathVariable long id) {
        return physiotherapistService.deleteWorkingHourForPhysiotherapist(id);
    }

    @DeleteMapping("/{id}/working-hours/delete-all")
    public ResponseEntity<?> deleteAllWorkingHours(@PathVariable long id) {
        return physiotherapistService.deleteAllWorkingDaysForPhysiotherapist(id);
    }

    @PutMapping("/working-hours/{id}/update")
    public ResponseEntity<WorkingHours> updateWorkingHours(@PathVariable long id,
            @RequestBody WorkingHours workingHours) {
        return physiotherapistService.updateWorkingDaysForPhysiotherapist(id, workingHours.getDayOfWeek(),
                workingHours.getStartTime(), workingHours.getEndTime());
    }
}
