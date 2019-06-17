package cz.muni.fi.pb138.videolibrary.manager;

import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.exception.*;
import java.util.Set;

public interface CategoryManager {

    /**
     * Creates new category in database.
     *
     * @param category category to be created.
     * @throws IllegalArgumentException when category is null.
     * @throws EntityValidationException when category has null attributes or
     *          name is empty.
     */
    void createCategory(Category category);

    /**
     * Deletes the category from database.
     *
     * @param category category to be deleted.
     * @throws IllegalArgumentException when category is null.
     */
    void deleteCategory(Category category);

    /**
     * Finds all categories in database.
     *
     * @return set of all categories in database.
     */
    Set<Category> findAllCategories();

    /**
     * Moves medium from one category to another.
     *
     * @param medium medium to be moved.
     * @param category category to be moved to.
     * @throws IllegalArgumentException when medium, from or to is null.
     * @throws IllegalEntityException when medium, from or to is not in db.
     * @throws EntityValidationException when medium, from or to has null
     *          or empty attributes.
     */
    void moveMedium(Medium medium, Category category);
}
