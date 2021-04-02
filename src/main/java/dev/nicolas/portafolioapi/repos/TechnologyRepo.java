package dev.nicolas.portafolioapi.repos;

import dev.nicolas.portafolioapi.entities.Technology;
import dev.nicolas.portafolioapi.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnologyRepo extends JpaRepository<Technology, Long> {
    Optional<Technology> findByName(String name);
    Optional<List<Technology>> findAllByUserProfile(UserProfile userProfile);
}
