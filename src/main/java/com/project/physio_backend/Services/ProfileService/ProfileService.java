package com.project.physio_backend.Services.ProfileService;

import com.project.physio_backend.Entities.Users.Profile;
import java.util.Optional;

public interface ProfileService {
    Optional<Profile> getProfileById(Long id);
    Optional<Profile> getProfileByUserId(Long userId);
    Profile createProfile(Profile profile);
    Profile updateProfile(Long id, Profile profileDetails);
    void deleteProfile(Long id);
}