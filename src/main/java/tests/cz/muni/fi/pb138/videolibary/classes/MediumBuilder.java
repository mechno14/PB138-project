package tests.cz.muni.fi.pb138.videolibary.classes;

import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Genre;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.entity.MediumType;

import java.time.Year;
import java.util.Set;

/**
 * @author Simona Bennárová
 */

public class MediumBuilder {

    private Long id;
    private String name;
    private MediumType mediumType;
    private int length;
    private Category category;
    private Set<String> actors;
    private Set<Genre> genres;
    private Year releaseYear;

    public MediumBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public MediumBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MediumBuilder mediumType(MediumType mediumType) {
        this.mediumType =  mediumType;
        return this;
    }

    public MediumBuilder length(int length) {
        this.length = length;
        return this;
    }

    public MediumBuilder category(Category category) {
        this.category = category;
        return this;
    }

    public MediumBuilder actors(Set<String> actors) {
        this.actors = actors;
        return this;
    }

    public MediumBuilder genres(Set<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public MediumBuilder releaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public Medium build() {
        Medium medium = new Medium();
        medium.setId(id);
        medium.setName(name);
        medium.setMediumType(mediumType);
        medium.setLength(length);
        medium.setCategory(category);
        medium.setActors(actors);
        medium.setGenres(genres);
        medium.setReleaseYear(releaseYear);
        return medium;

    }

}
