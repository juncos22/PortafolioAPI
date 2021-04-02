package dev.nicolas.portafolioapi.services;

import dev.nicolas.portafolioapi.entities.Thumbnail;
import dev.nicolas.portafolioapi.repos.ThumbnailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ThumbnailService {
    private final ThumbnailRepo thumbnailRepo;

    @Autowired
    public ThumbnailService(ThumbnailRepo thumbnailRepo) {
        this.thumbnailRepo = thumbnailRepo;
    }
    public Thumbnail save(Thumbnail thumbnail) {
        return thumbnailRepo.save(thumbnail);
    }
}
