package tests.cz.muni.fi.pb138.videolibary.classes;

import cz.muni.fi.pb138.videolibrary.entity.Category;

/**
 * @author Simona Bennárová
 */


public class CategoryBuilder {
    String name;

    public CategoryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public Category build() {
        Category category = new Category();
        category.setName(name);
        return category;
    }


}
