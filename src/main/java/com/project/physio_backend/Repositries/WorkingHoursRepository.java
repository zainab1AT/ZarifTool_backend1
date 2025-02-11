package com.project.physio_backend.Repositries;

import org.springframework.stereotype.Repository;

import com.project.physio_backend.Entities.Physiotherapists.WorkingHours;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long>{

}