package cz.muni.fi.pb138.videolibrary.manager;

import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.exception.EntityValidationException;
import cz.muni.fi.pb138.videolibrary.exception.IllegalEntityException;

import java.util.Set;

public interface MediumManager {

    /**
     * Creates new medium in database.
     *
     * @param medium medium to be created.
     * @throws IllegalArgumentException when medium is null.
     * @throws EntityValidationException when medium has null attributes or
     *          name is empty.
     * @throws IllegalEntityException when medium has already id.
     */
    void createMedium(Medium medium);

    /**
     * Deletes the medium from database.
     *
     * @param medium medium to be deleted.
     * @throws IllegalArgumentException when medium is null.
     * @throws IllegalEntityException when medium is not in database.
     */
    void deleteMedium(Medium medium);

    /**
     * Finds medium by its id in database.
     *
     * @param id id of the medium to be found.
     * @return medium with given id or null if such medium isn't in db.
     * @throws IllegalArgumentException when id is null.
     */
    Medium findMediumById(Long id);

    /**
     * Finds medium by its name in database.
     *
     * @param name attribute name of medium to be found.
     * @return medium with given name or null if such medium isn't in db.
     * @throws IllegalArgumentException when name is null or empty.
     */
    Medium findMediumByName(String name);

}
