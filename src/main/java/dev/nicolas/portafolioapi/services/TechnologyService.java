package dev.nicolas.portafolioapi.services;

import dev.nicolas.portafolioapi.entities.Technology;
import dev.nicolas.portafolioapi.entities.UserProfile;
import dev.nicolas.portafolioapi.repos.TechnologyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnologyService {
    private final TechnologyRepo technologyRepo;

    @Autowired
    public TechnologyService(TechnologyRepo technologyRepo) {
        this.technologyRepo = technologyRepo;
    }
    public Technology save(Technology technology) {
        return technologyRepo.save(technology);
    }
    public Optional<Technology> find(String name) {
        return technologyRepo.findByName(name);
    }
    public Optional<List<Technology>> findAll(UserProfile userProfile) {
        return technologyRepo.findAllByUserProfile(userProfile);
    }
}
