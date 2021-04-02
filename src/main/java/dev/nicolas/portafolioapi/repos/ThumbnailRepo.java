package dev.nicolas.portafolioapi.repos;

import dev.nicolas.portafolioapi.entities.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbnailRepo extends JpaRepository<Thumbnail, Long> {
}
