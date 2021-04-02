package dev.nicolas.portafolioapi.services;

import dev.nicolas.portafolioapi.entities.UserProfile;
import dev.nicolas.portafolioapi.repos.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {
    private final UserProfileRepo userProfileRepo;

    @Autowired
    public UserProfileService(UserProfileRepo userProfileRepo) {
        this.userProfileRepo = userProfileRepo;
    }

    public UserProfile save(UserProfile userProfile) {
        return userProfileRepo.save(userProfile);
    }
    public Optional<UserProfile> find(String name) {
        return userProfileRepo.findByName(name);
    }
}
