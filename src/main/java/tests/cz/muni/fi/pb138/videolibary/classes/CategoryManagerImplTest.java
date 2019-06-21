package tests.cz.muni.fi.pb138.videolibary.classes;


import cz.muni.fi.pb138.videolibrary.XMLDBManagerImpl;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.exception.EntityValidationException;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;


public class CategoryManagerImplTest {
    private CategoryManagerImpl manager;

    private CategoryBuilder categoryDocumentaries() {
        return new CategoryBuilder()
                .name("TEST123456");
    }

    private CategoryBuilder categoryMovies() {
        return new CategoryBuilder()
                .name("TEST1234");

    }

    private CategoryBuilder categoryWithNullName() {
        return new CategoryBuilder()
                .name(null);
    }

    @BeforeEach
    void setXmlDBManager() throws Exception {
        XMLDBManagerImpl xmlDBManager = new XMLDBManagerImpl();
        manager =  new CategoryManagerImpl(xmlDBManager);
    }

    @Test
    void createCategoryWithNull() {
        Assertions.assertThrows(IllegalArgumentException.class,()-> {
            manager.createCategory(null);
        });
    }

    @Test
    void createDupliciteCategory() {
        Category category = categoryDocumentaries().build();
        manager.createCategory(category);
        Assertions.assertFalse(manager.createCategory(category));
        manager.deleteCategory(category);
    }

    @Test
    void createCategoryWithNullName() {
        Category category = categoryWithNullName().build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.createCategory(category);
        });
    }

    @Test
    void createCategoryWithEmptyName() {
        Category category = new CategoryBuilder().name("").build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.createCategory(category);
        });
    }

    @Test
    void deleteCategoryWithNullName() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            manager.deleteCategory(null);
        });
    }

    @Test
    void deleteCategoryWithEmptyName() {
        Category category = new CategoryBuilder().name("").build();
        Assertions.assertThrows(EntityValidationException.class, ()-> {
            manager.deleteCategory(category);
        });
    }

    @Test
    void findAllCathegories() {
        Category category1 = categoryDocumentaries().build();
        Category category2 = categoryMovies().build();
        Set<Category> allCategories = manager.findAllCategories();
        int numOfCat = allCategories.size();
        manager.createCategory(category1);
        manager.createCategory(category2);
        allCategories = manager.findAllCategories();
        Assertions.assertTrue(allCategories.size() == numOfCat + 2);
        Assertions.assertTrue(allCategories.contains(category1));
        Assertions.assertTrue(allCategories.contains(category2));
        manager.deleteCategory(category1);
        manager.deleteCategory(category2);
    }

    @Test
    void deleteCathegory() throws Exception {
        Category category1 = categoryDocumentaries().build();
        Category category2 = categoryMovies().build();
        Set<Category> allCategories = manager.findAllCategories();
        int numOfElements = allCategories.size();
        manager.createCategory(category1);
        manager.createCategory(category2);
        allCategories = manager.findAllCategories();
        Assertions.assertTrue(allCategories.size() == (numOfElements + 2));
        manager.deleteCategory(category1);
        allCategories = manager.findAllCategories();
        Assertions.assertTrue(allCategories.size() == (numOfElements + 1));
        Assertions.assertFalse(allCategories.contains(category1));
        manager.deleteCategory(category2);
    }



}
