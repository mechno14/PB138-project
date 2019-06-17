package cz.muni.fi.pb138.videolibrary.manager;

import cz.muni.fi.pb138.videolibrary.NativeXMLDatabaseManager;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.entity.Medium;
import cz.muni.fi.pb138.videolibrary.exception.EntityValidationException;
import cz.muni.fi.pb138.videolibrary.exception.IllegalEntityException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class CategoryManagerImpl implements CategoryManager {

    @Autowired
    NativeXMLDatabaseManager databaseManager;


    private void validate(Category category) {
        if (category == null)
            throw new IllegalArgumentException("Category cannot be null.");

        if (category.getName() == null ||
                category.getMedia() == null)
            throw new EntityValidationException("Entity has null attribute(s).");

        if (category.getName().isEmpty())
            throw new EntityValidationException("Name cannot be empty.");
    }


    @Override
    public void createCategory(Category category) {
        validate(category);

        if (category.getId() != null)
            throw new IllegalEntityException("Entity has already id.");

        databaseManager.createCategory(category);
    }

    @Override
    public void updateCategory(Category category) {
        validate(category);

        if (category.getId() == null || findCategoryById(category.getId()) == null)
            throw new IllegalEntityException("Entity is not in db.");

        databaseManager.updateCategory(category);
    }

    @Override
    public void deleteCategory(Category category) {
        validate(category);

        if (category.getId() == null || findCategoryById(category.getId()) == null)
            throw new IllegalEntityException("Entity is not in db.");

        databaseManager.deleteCategory(category);
    }

    @Override
    public Category findCategoryById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Id cannot be null");

        return databaseManager.findCategoryById(id);
    }

    @Override
    public Set<Category> findAllCategories() {
        return databaseManager.findAllCategories();
    }

    @Override
    public void moveMedium(Medium medium, Category from, Category to) {

    }
}
