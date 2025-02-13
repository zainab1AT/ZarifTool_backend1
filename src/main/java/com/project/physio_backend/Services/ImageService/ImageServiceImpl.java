package com.project.physio_backend.Services.ImageService;

import com.cloudinary.Cloudinary;
import com.project.physio_backend.Entities.Excercises.Exercise;
import com.project.physio_backend.Entities.Image.Image;
import com.project.physio_backend.Entities.Image.ImageType;
import com.project.physio_backend.Entities.Physiotherapists.Physiotherapist;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Reports.Report;
import com.project.physio_backend.Repositories.ExerciseRepository;
import com.project.physio_backend.Repositories.ImageRepository;
import com.project.physio_backend.Repositories.PhysiotherapistRepository;
import com.project.physio_backend.Repositories.ProblemRepository;
import com.project.physio_backend.Repositories.ReportRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;
    private final ProblemRepository problemRepository;
    private final ExerciseRepository exerciseRepository;
    private final ReportRepository reportRepository;
    private final PhysiotherapistRepository physiotherapistRepository;

    public String uploadImageForProblem(MultipartFile multipartFile, Long problemId) {
        try {
            Optional<Problem> optionalProblem = problemRepository.findById(problemId);
            if (optionalProblem.isEmpty()) {
                throw new RuntimeException("Problem not found");
            }
            Problem problem = optionalProblem.get();

            String fileName = multipartFile.getOriginalFilename();

            // Upload to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                    Map.of("public_id", UUID.randomUUID().toString()));

            String imageURL = uploadResult.get("url").toString();

            // Save in database
            Image image = new Image();
            image.setName(fileName);
            image.setUrl(imageURL);
            image.setType(ImageType.PROBLEM);

            imageRepository.save(image);

            // Link image to problem
            problem.setImage(image);
            problemRepository.save(problem);

            return imageURL;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    public String uploadImageForExercise(MultipartFile multipartFile, Long exerciseId) {
        try {
            Optional<Exercise> optionalExercise = exerciseRepository.findById(exerciseId);
            if (optionalExercise.isEmpty()) {
                throw new RuntimeException("Exercise not found");
            }
            Exercise exercise = optionalExercise.get();

            String fileName = multipartFile.getOriginalFilename();

            // Upload to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                    Map.of("public_id", UUID.randomUUID().toString()));

            String imageURL = uploadResult.get("url").toString();

            // Save in database
            Image image = new Image();
            image.setName(fileName);
            image.setUrl(imageURL);
            image.setType(ImageType.EXERCISE);

            imageRepository.save(image);

            // Link image to problem
            exercise.setImage(image);
            exerciseRepository.save(exercise);

            return imageURL;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    public String uploadImageForReport(MultipartFile multipartFile, Long reportId){
        try {
            Optional<Report> optionalReport = reportRepository.findById(reportId);
            if (optionalReport.isEmpty()) {
                throw new RuntimeException("Report not found");
            }
            Report report = optionalReport.get();

            String fileName = multipartFile.getOriginalFilename();

            // Upload to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                    Map.of("public_id", UUID.randomUUID().toString()));

            String imageURL = uploadResult.get("url").toString();

            // Save in database
            Image image = new Image();
            image.setName(fileName);
            image.setUrl(imageURL);
            image.setType(ImageType.REPORT);

            imageRepository.save(image);

            // Link image to problem
            report.setImage(image);
            reportRepository.save(report);

            return imageURL;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    public String uploadImageForPhysiotherapist(MultipartFile multipartFile, Long physiotherapistId){
        try {
            Optional<Physiotherapist> optionalPhysiotherapist = physiotherapistRepository.findById(physiotherapistId);
            if (optionalPhysiotherapist.isEmpty()) {
                throw new RuntimeException("Physiotherapist not found");
            }
            Physiotherapist physiotherapist = optionalPhysiotherapist.get();

            String fileName = multipartFile.getOriginalFilename();

            // Upload to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                    Map.of("public_id", UUID.randomUUID().toString()));

            String imageURL = uploadResult.get("url").toString();

            // Save in database
            Image image = new Image();
            image.setName(fileName);
            image.setUrl(imageURL);
            image.setType(ImageType.PHYSIOTHERAPISTS);

            imageRepository.save(image);

            // Link image to problem
            physiotherapist.setImage(image);
            physiotherapistRepository.save(physiotherapist);

            return imageURL;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }
}