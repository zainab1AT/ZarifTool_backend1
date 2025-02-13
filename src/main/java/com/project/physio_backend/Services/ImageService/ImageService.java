package com.project.physio_backend.Services.ImageService;

import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.io.IOException;

public interface  ImageService {
    public String uploadImageForProblem(MultipartFile multipartFile, Long problemId) throws IOException;
    public String uploadImageForExercise(MultipartFile multipartFile, Long exerciseId) throws IOException;
    public String uploadImageForReport(MultipartFile multipartFile, Long reportId) throws IOException;
    public String uploadImageForPhysiotherapist(MultipartFile multipartFile, Long physiotherapistId) throws IOException;

}