package cz.muni.fi.pb138.videolibrary.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Category {
    private Long id;
    private String name;
    private Set<Medium> media;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Set<Medium> media) {
        this.name = name;
        this.media = media;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Medium> getMedia() {
        return Collections.unmodifiableSet(media);
    }

    public void setMedia(Set<Medium> media) {
        this.media = media;
    }

    public void addMedium(Medium medium) {
        if (media == null) {
            media = new HashSet<>();
        }

        if (medium != null) {
            media.add(medium);
            medium.getCategory().removeMedium(medium);
            medium.setCategory(this);
        }
    }

    public void removeMedium(Medium medium) {
        medium.setCategory(null);
        media.remove(medium);
    }
}
