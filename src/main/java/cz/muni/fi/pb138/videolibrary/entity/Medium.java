package cz.muni.fi.pb138.videolibrary.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name="medium")
public class Medium {
    private Long id;
    private String name;
    private MediumType mediumType;
    private int length;
    private Category category;
    private Set<String> actors;
    private Set<Genre> genres;
    private int releaseYear;

    public Medium() {
    }


    public Medium(String name, MediumType mediumType, Category category,
                  Genre genre, int releaseYear) {
        this.name = name;
        this.mediumType = mediumType;
        this.category = category;
        Set<Genre> genres = new HashSet<>();
        genres.add(genre);
        this.genres = genres;
        Set<String> actors = new HashSet<>();
        this.actors = actors;
        this.releaseYear = releaseYear;
    }

    public Medium(String name, MediumType mediumType, int length,
                  Category category, Set<String> actors, Set<Genre> genres,
                  int releaseYear) {
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

    @XmlAttribute
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public MediumType getMediumType() {
        return mediumType;
    }

    @XmlElement
    public void setMediumType(MediumType mediumType) {
        this.mediumType = mediumType;
    }

    public int getLength() {
        return length;
    }

    @XmlElement
    public void setLength(int length) {
        this.length = length;
    }

    public Category getCategory() {
        return category;
    }

    @XmlElement
    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<String> getActors() {
        return actors;
    }

    @XmlElementWrapper
    @XmlElement(name="actor")
    public void setActors(Set<String> actors) {
        this.actors = actors;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    @XmlElementWrapper
    @XmlElement(name="genre")
    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    @XmlElement
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
