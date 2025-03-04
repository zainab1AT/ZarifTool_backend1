package com.project.physio_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.physio_backend.Entities.Prize.Prize;

@Repository
public interface PrizeRepository extends JpaRepository<Prize, Long>{
    
}
