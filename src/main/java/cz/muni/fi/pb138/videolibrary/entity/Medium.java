package cz.muni.fi.pb138.videolibrary.entity;

import java.time.Year;
import java.util.Set;

public class Medium {
    private Long id;
    private String name;
    private MediumType mediumType;
    private int length;
    private Category category;
    private Set<String> actors;
    private Set<Genre> genres;
    private Year releaseYear;

    public Medium() {
    }

    public Medium(String name, MediumType mediumType, int length,
                  Category category, Set<String> actors, Set<Genre> genres,
                  Year releaseYear) {
        this.name = name;
        this.mediumType = mediumType;
        this.length = length;
        this.category = category;
        this.actors = actors;
        this.genres = genres;
        this.releaseYear = releaseYear;
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

    public MediumType getMediumType() {
        return mediumType;
    }

    public void setMediumType(MediumType mediumType) {
        this.mediumType = mediumType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<String> getActors() {
        return actors;
    }

    public void setActors(Set<String> actors) {
        this.actors = actors;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
    }
}
