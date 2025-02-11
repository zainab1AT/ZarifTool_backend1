package com.project.physio_backend.Services.ProfileService;

import com.project.physio_backend.Entities.Users.Profile;
import com.project.physio_backend.Exceptions.Users.ProfileNotFoundException;
import com.project.physio_backend.Repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    @Override
    public Optional<Profile> getProfileByUserId(Long userId) {
        return profileRepository.findByUser_UserID(userId);
    }

    @Override
    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Profile updateProfile(Long id, Profile profileDetails) {
        return profileRepository.findById(id).map(profile -> {
            profile.setBio(profileDetails.getBio());
            profile.setProfilePictureUri(profileDetails.getProfilePictureUri());
            profile.setHeight(profileDetails.getHeight());
            profile.setWeight(profileDetails.getWeight());
            profile.setDateOfBirth(profileDetails.getDateOfBirth());
            profile.setGender(profileDetails.getGender());
            profile.setLocation(profileDetails.getLocation());
            return profileRepository.save(profile);
        }).orElseThrow(() -> new ProfileNotFoundException(id));
    }

    @Override
    public void deleteProfile(Long id) {
        if (!profileRepository.existsById(id)) {
            throw new ProfileNotFoundException(id);
        }
        profileRepository.deleteById(id);
    }
}
