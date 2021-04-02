package dev.nicolas.portafolioapi.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String description;
    @ManyToOne
    private Technology technology;
    @ManyToOne
    private UserProfile userProfile;
    @OneToMany
    private List<Thumbnail> thumbnails = new ArrayList<>();

    public Project() {
    }

    public Project(String title, String description, Technology technology, UserProfile userProfile, List<Thumbnail> thumbnails) {
        this.title = title;
        this.description = description;
        this.technology = technology;
        this.userProfile = userProfile;
        this.thumbnails = thumbnails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
    }
}
