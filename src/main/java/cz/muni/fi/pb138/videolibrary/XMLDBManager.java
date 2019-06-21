package cz.muni.fi.pb138.videolibrary;

import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import org.xmldb.api.base.XMLDBException;

import java.util.Map;
import java.util.Set;

/**
 * @author Denis Hambalek
 */
public interface XMLDBManager {
    /**
     * Safely close DB connection.
     * @throws XMLDBException on lost connection
     */
    void close() throws XMLDBException;

    /**
     * Get first free ID in database.
     * @return first free ID in database.
     */
    Long getMaxId();

    /**
     * Creates new Category in database.
     * @param category to be created in database
     * @return false if category with given name already exist, true otherwise
     */
    boolean createCategory(Category category);

    /**
     * Deletes exist Category in database.
     * @param category to be deleted in database
     */
    void deleteCategory(Category category);

    /**
     * Creates new Medium in database.
     * @param medium to be created in database
     */
    void createMedium(Medium medium);

    /**
     * Delete Medium by specified ID.
     * @param mediumId ID of medium to be deleted
     */
    void deleteMedium(String mediumId);

    /**
     * Creates new Medium with medium query in explicite specified Category in database.
     * @param mediumQuery to be created in database
     * @param category to be created in
     */
    void addMediumToCategory(String mediumQuery, String category);

    /**
     * Deletes exist Medium in database.
     * @param medium to be deleted
     */
    void deleteMedium(Medium medium);

    /**
     * Finds Medium with specific ID.
     * @param mediumId to be found
     * @return Medium with given ID
     */
    Medium findMediumById(String mediumId);

    /**
     * Finds Medium with given Name.
     * @param mediumName medium name to be found
     * @return Medium Query with given Name
     */
    Set<Medium> findMediumByName(String mediumName);

    /**
     * Finds name of category where is medium with specified id.
     * @param mediumId in category
     * @return category name
     */
    String findCategoryByMediumId(String mediumId);

    /**
     * Finds all Categories.
     * @return Set of Names of categories.
     */
    Set<Category> findAllCategories();

    /**
     * Finds all Mediums in given Category
     * @param category to be checked
     * @return Mediums Query in given Category
     */
    Set<Medium> findAllMediumsByCategory(String category);

    /**
     * Imports data into database.
     * @param categoryMap data to be imported
     */
    void importIntoDatabase(Map<Category, Set<Medium>> categoryMap);

    /**
     * Exports query from database.
     * @return exported query from database
     */
    Map<Category, Set<Medium>> exportQueryFromDatabase();
}
