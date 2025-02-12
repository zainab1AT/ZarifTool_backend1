package com.project.physio_backend.Services.ProfileService;

import com.project.physio_backend.Entities.Users.Profile;
import java.util.Optional;

public interface ProfileService {
    Optional<Profile> getProfileById(Long profileID);

    Optional<Profile> getProfileByUserId(Long userID);

    Profile createProfile(Profile profile);

    Profile updateProfile(Long profileID, Profile profileDetails);

    void deleteProfile(Long profileID);
}