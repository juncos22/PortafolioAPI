package dev.nicolas.portafolioapi.services;

import dev.nicolas.portafolioapi.entities.Project;
import dev.nicolas.portafolioapi.entities.UserProfile;
import dev.nicolas.portafolioapi.repos.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepo projectRepo;

    @Autowired
    public ProjectService(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }
    public Project save(Project project) {
        return projectRepo.save(project);
    }
    public List<Project> findAll(UserProfile userProfile) {
        return projectRepo.findAllByUserProfile(userProfile);
    }
}
