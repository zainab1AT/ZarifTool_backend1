package com.project.physio_backend.Repositories;

import com.project.physio_backend.Entities.Users.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser_UserID(Long userId);
}