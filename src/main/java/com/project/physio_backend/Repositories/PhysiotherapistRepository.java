package com.project.physio_backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.physio_backend.Entities.Physiotherapists.Physiotherapist;
import com.project.physio_backend.Entities.Users.Location;

@Repository
public interface PhysiotherapistRepository extends JpaRepository<Physiotherapist, Long> {
    List<Physiotherapist> findByLocation(Location location);
}
