package dev.nicolas.portafolioapi.repos;

import dev.nicolas.portafolioapi.entities.Project;
import dev.nicolas.portafolioapi.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {
    List<Project> findAllByUserProfile(UserProfile userProfile);
}
