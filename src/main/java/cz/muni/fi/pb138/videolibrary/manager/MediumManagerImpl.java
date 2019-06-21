package cz.muni.fi.pb138.videolibrary.manager;

import cz.muni.fi.pb138.videolibrary.entity.Category;

import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.exception.EntityValidationException;
import cz.muni.fi.pb138.videolibrary.exception.IllegalEntityException;

import java.util.Set;

public class MediumManagerImpl implements MediumManager {


    private XMLDBManager databaseManager;

    public MediumManagerImpl(XMLDBManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    private void validate(Medium medium) {
        if (medium == null)
            throw new IllegalArgumentException("Medium cannot be null.");

        if (medium.getName() == null ||
                medium.getMediumType() == null ||
                medium.getCategory() == null ||
                medium.getActors() == null ||
                medium.getGenres() == null)
            throw new EntityValidationException("Entity has null attribute(s).");

        if (medium.getLength() < 0)
            throw new EntityValidationException("Length cannot be negative.");

        if (medium.getName().isEmpty())
            throw new EntityValidationException("Name cannot be empty.");


        // tuto podmienku by som dal prec
        if (medium.getReleaseYear() < 1900)
            throw new EntityValidationException("Wrong year.");
    }

    @Override
    public void createMedium(Medium medium) {
        validate(medium);

        if (medium.getId() != null)
            throw new IllegalEntityException("Entity has already id.");
        databaseManager.createMedium(medium);
    }

    @Override
    public void deleteMedium(Medium medium) {
        validate(medium);

        if (medium.getId() == null || findMediumById(medium.getId()) == null)
            throw new IllegalEntityException("Entity is not in db.");

        databaseManager.deleteMedium(medium);
    }

    @Override
    public Medium findMediumById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id cannot be null");

        return databaseManager.findMediumById(id.toString());
    }

    @Override
    public Set<Medium> findMediumByName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name cannot be null or empty.");

        return databaseManager.findMediumByName(name);
    }

    @Override
    public Set<Medium> findAllMediaByCategory(Category category) {
        if (category == null)
            throw new IllegalArgumentException("Category cannot be null");

        return databaseManager.findAllMediumsByCategory(category.getName());
    }
}
