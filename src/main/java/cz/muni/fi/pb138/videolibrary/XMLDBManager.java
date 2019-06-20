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
    public void close() throws XMLDBException;

    /**
     * Get first free ID in database.
     * @return first free ID in database.
     */
    public Long getFirstFreeId();

    /**
     * Creates new Category in database.
     * @param category to be created in database
     * @return false if category with given name already exist, true otherwise
     */
    public boolean createCategory(Category category);

    /**
     * Deletes exist Category in database.
     * @param category to be deleted in database
     */
    public void deleteCategory(Category category);

    /**
     * Creates new Medium in database.
     * @param medium to be created in database
     */
    public void createMedium(Medium medium);

    /**
     * Delete Medium by specified ID.
     * @param mediumId ID of medium to be deleted
     */
    public void deleteMedium(String mediumId);

    /**
     * Creates new Medium with medium query in explicite specified Category in database.
     * @param mediumQuery to be created in database
     * @param category to be created in
     */
    public void addMediumToCategory(String mediumQuery, String category);

    /**
     * Deletes exist Medium in database.
     * @param medium to be deleted
     */
    public void deleteMedium(Medium medium);

    /**
     * Finds Medium with specific ID.
     * @param mediumId to be found
     * @return Medium with given ID
     */
    public Medium findMediumById(String mediumId);

    /**
     * Finds Medium with given Name.
     * @param mediumName medium name to be found
     * @return Medium Query with given Name
     */
    public Set<Medium> findMediumByName(String mediumName);

    /**
     * Finds name of category where is medium with specified id.
     * @param mediumId in category
     * @return category name
     */
    public String findCategoryByMediumId(String mediumId);

    /**
     * Finds all Categories.
     * @return Set of Names of categories.
     */
    public Set<Category> findAllCategories();

    /**
     * Finds all Mediums in given Category
     * @param category to be checked
     * @return Mediums Query in given Category
     */
    public Set<Medium> findAllMediumsByCategory(String category);

    /**
     * Imports data into database.
     * @param categoryMap data to be imported
     */
    public void importIntoDatabase(Map<Category, Set<Medium>> categoryMap);

    /**
     * Exports query from database.
     * @return exported query from database
     */
    public Set<Medium> exportQueryFromDatabase();
}
