package com.project.physio_backend.Repositries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.physio_backend.Entities.Physiotherapists.Physiotherapist;

@Repository
public interface PhysiotherapistRepositry extends JpaRepository<Physiotherapist, Long>{
    
}
