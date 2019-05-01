package cz.muni.fi.pb138.videolibrary.DAO;

import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.exception.*;
import java.util.Set;

public interface CategoryDAO {

    /**
     * Creates new category in database.
     *
     * @param category category to be created.
     * @throws IllegalArgumentException when category is null.
     * @throws EntityValidationException when category has null attributes or
     *          name is empty.
     * @throws IllegalEntityException when category has already id.
     */
    void createCategory(Category category);

    /**
     * Updates the category in database.
     *
     * @param category category to be updated.
     * @throws IllegalArgumentException when category is null.
     * @throws EntityValidationException when category has null attributes or
     *          name is empty.
     * @throws IllegalEntityException when category is not in database.
     */
    void updateCategory(Category category);

    /**
     * Deletes the category from database.
     *
     * @param category category to be deleted.
     * @throws IllegalArgumentException when category is null.
     * @throws IllegalEntityException when category is not in database.
     */
    void deleteCategory(Category category);

    /**
     * Finds category by its id in database.
     *
     * @param id id of the category to be found.
     * @return category with given id or null if such category isn't in db.
     * @throws IllegalArgumentException when id is null.
     */
    Category findCategoryById(Long id);

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
     * @param from category to be moved from.
     * @param to category to be moved to.
     * @throws IllegalArgumentException when medium, from or to is null.
     * @throws IllegalEntityException when medium, from or to is not in db.
     * @throws EntityValidationException when medium, from or to has null
     *          or empty attributes.
     */
    void moveMedium(Medium medium, Category from, Category to);
}
