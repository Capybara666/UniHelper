package com.github.UniHelper.model.categories;

import java.util.ArrayList;
import java.util.UUID;

public interface CategoriesModel {

    void addCategory(Category category);

    void updateCategoryById(UUID id, Category category);

    void deleteCategory(Category category);

    ArrayList<Category> getAllCategories();

    void setCategories(ArrayList<Category> categories);
}
