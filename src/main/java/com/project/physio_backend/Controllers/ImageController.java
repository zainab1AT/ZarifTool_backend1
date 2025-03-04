package com.project.physio_backend.Controllers;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.physio_backend.Entities.Image.Image;
import com.project.physio_backend.Services.ImageService.ImageServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/images")
@CrossOrigin
public class ImageController {

    private final ImageServiceImpl imageService;

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable("id") Long id) {
        Optional<Image> imageOptional = imageService.getImageById(id);

        if (imageOptional.isPresent()) {
            return ResponseEntity.ok(imageOptional.get());
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if image not found
        }
    }

    @PostMapping("/upload/problem/{problemId}")
    public ResponseEntity<Map<String, String>> uploadImageForProblem(
            @PathVariable Long problemId,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String imageURL = imageService.uploadImageForProblem(multipartFile, problemId);
        return ResponseEntity.ok(Map.of("imageURL", imageURL));
    }

    @PostMapping("/upload/exercise/{exerciseId}")
    public ResponseEntity<Map<String, String>> uploadImageForExercise(
            @PathVariable Long exerciseId,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String imageURL = imageService.uploadImageForExercise(multipartFile, exerciseId);
        return ResponseEntity.ok(Map.of("imageURL", imageURL));
    }

    @PostMapping("/upload/report/{reportId}")
    public ResponseEntity<Map<String, String>> uploadImageForReport(
            @PathVariable Long reportId,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String imageURL = imageService.uploadImageForReport(multipartFile, reportId);
        return ResponseEntity.ok(Map.of("imageURL", imageURL));
    }

    @PostMapping("/upload/physiotherapist/{physiotherapistId}")
    public ResponseEntity<Map<String, String>> uploadImageForPhysiotherapist(
            @PathVariable Long physiotherapistId,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String imageURL = imageService.uploadImageForPhysiotherapist(multipartFile, physiotherapistId);
        return ResponseEntity.ok(Map.of("imageURL", imageURL));
    }

}
