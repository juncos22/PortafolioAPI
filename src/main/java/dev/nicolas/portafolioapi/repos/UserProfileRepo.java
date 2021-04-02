package dev.nicolas.portafolioapi.repos;

import dev.nicolas.portafolioapi.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByName(String name);
}
