package dev.nicolas.portafolioapi.entities;

import javax.persistence.*;

@Entity
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    @OneToOne
    private Thumbnail thumbnail;
    @ManyToOne
    private UserProfile userProfile;

    public Technology() {
    }

    public Technology(String name, String description, Thumbnail thumbnail, UserProfile userProfile) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.userProfile = userProfile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
