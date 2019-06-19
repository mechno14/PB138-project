package cz.muni.fi.pb138.videolibrary.manager;

import cz.muni.fi.pb138.videolibrary.XMLDBManagerImpl;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.exception.EntityValidationException;

import java.util.Set;

public class CategoryManagerImpl implements CategoryManager {

<<<<<<< HEAD
    //@Autowired
    XMLDBManagerImpl databaseManager;
=======
    NativeXMLDatabaseManager databaseManager;
>>>>>>> GUI

    public CategoryManagerImpl(XMLDBManagerImpl databaseManager) {
        this.databaseManager = databaseManager;
    }

    private void validate(Category category) {
        if (category == null)
            throw new IllegalArgumentException("Category cannot be null.");

        if (category.getName() == null)
            throw new EntityValidationException("Entity has null attribute(s).");

        if (category.getName().isEmpty())
            throw new EntityValidationException("Name cannot be empty.");
    }


    @Override
    public void createCategory(Category category) {
        validate(category);

        databaseManager.createCategory(category);
    }

    @Override
    public void deleteCategory(Category category) {
        validate(category);

        databaseManager.deleteCategory(category);
    }

    @Override
    public Set<Category> findAllCategories() {
        return databaseManager.findAllCategories();
    }

    @Override
    public void moveMedium(Medium medium, Category category) {
        databaseManager.deleteMedium(medium);
        medium.setCategory(category);
        databaseManager.createMedium(medium);
    }
}
