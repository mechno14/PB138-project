package tests.cz.muni.fi.pb138.videolibary.classes;


import cz.muni.fi.pb138.videolibrary.XMLDBManagerImpl;
import cz.muni.fi.pb138.videolibrary.entity.Category;
import cz.muni.fi.pb138.videolibrary.exception.EntityValidationException;
import cz.muni.fi.pb138.videolibrary.manager.CategoryManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;


public class CategoryManagerImplTest {
    XMLDBManagerImpl xmlDBManager;
    private CategoryManagerImpl manager = new CategoryManagerImpl(xmlDBManager);

    private CategoryBuilder categoryDocumentaries() {
        return new CategoryBuilder()
                .name("Documentaries");
    }

    private CategoryBuilder categoryMovies() {
        return new CategoryBuilder()
                .name("Movies");

    }

    private CategoryBuilder categoryWithNullName() {
        return new CategoryBuilder()
                .name(null);
    }

    @Test
    void createCategoryWithNull() {
        Assertions.assertThrows(IllegalAccessException.class,()-> {
            manager.createCategory(null);
        });
    }

    @Test
    void createDupliciteCategory() {
        Category category = categoryDocumentaries().build();
        manager.createCategory(category);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            manager.createCategory(category);
        });
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

    void addTwoCategories(){
        Category category1 = categoryDocumentaries().build();
        Category category2 = categoryMovies().build();
        manager.createCategory(category1);
        manager.createCategory(category2);
    }

    @Test
    void findAllCathegories() {
        addTwoCategories();
        Set<Category> allCategories = manager.findAllCategories();
        Assertions.assertTrue(allCategories.size() == 2);
        Assertions.assertTrue(allCategories.contains(categoryDocumentaries().build()));
        Assertions.assertTrue(allCategories.contains(categoryMovies().build()));
    }

    @Test
    void deleteCathegory() {
        addTwoCategories();
        Set<Category> allCategories = manager.findAllCategories();
        Assertions.assertTrue(allCategories.size() == 2);
        manager.deleteCategory(categoryDocumentaries().build());
        Assertions.assertTrue(allCategories.size() == 1);
        Assertions.assertTrue(!allCategories.contains(categoryDocumentaries().build()));
        Assertions.assertTrue(allCategories.contains(categoryMovies().build()));
    }



}
