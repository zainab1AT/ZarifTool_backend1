package com.project.physio_backend.Repositries;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.physio_backend.Entities.Progress.Progress;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
}