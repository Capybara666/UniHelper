import com.github.UniHelper.model.categories.CategoriesModel;
import com.github.UniHelper.model.categories.Category;
import com.github.UniHelper.model.categories.DefaultCategoriesModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

public class DefaultCategoriesModelTest {

    CategoriesModel categoriesModel;

    @BeforeEach
    void getModelInstance() {
        categoriesModel = DefaultCategoriesModel.getInstance();
    }

    @Test
    void given_empty_set_in_setCategories_getAllCategories_should_return_empty_set() {
        // Given
        ArrayList<Category> empty = new ArrayList<>();

        // When
        categoriesModel.setCategories(empty);

        // Then
        ArrayList<Category> returned = categoriesModel.getAllCategories();

        Assertions.assertTrue(returned.isEmpty());
    }

    @Test
    void given_non_empty_set_in_setCategories_getAllCategories_should_return_equal_set() {
        // Given
        Category c1 = new Category("n1", Color.WHITE);
        Category c2 = new Category("n2", Color.RED);
        ArrayList<Category> givenCategories = new ArrayList<>();
        givenCategories.add(c1);
        givenCategories.add(c2);

        // When
        categoriesModel.setCategories(givenCategories);
        ArrayList<Category> returnedCategories = categoriesModel.getAllCategories();

        // Then
        Assertions.assertEquals(givenCategories, returnedCategories);
    }

    @Test
    void deleteCategory_should_delete_given_category_if_it_exists_and_do_nothing_otherwise() {
        // Given
        Category c1 = new Category("n1", Color.WHITE);
        Category c2 = new Category("n2", Color.RED);
        Category c3 = new Category("n3", Color.BLACK);
        ArrayList<Category> givenCategories = new ArrayList<>();
        givenCategories.add(c1);
        givenCategories.add(c2);
        givenCategories.add(c3);
        categoriesModel.setCategories(givenCategories);

        // When
        categoriesModel.deleteCategory(null);
        categoriesModel.deleteCategory(new Category("n1", Color.WHITE));
        categoriesModel.deleteCategory(new Category("n3", Color.RED));
        categoriesModel.deleteCategory(c2);
        ArrayList<Category> returnedCategories = categoriesModel.getAllCategories();

        // Then
        Assertions.assertFalse(returnedCategories.contains(c1));
        Assertions.assertFalse(returnedCategories.contains(c2));
        Assertions.assertTrue(returnedCategories.contains(c3));
    }

    @Test
    void addCategory_should_only_add_it_if_it_is_not_present() {
        // Given
        Category c1 = new Category("n1", Color.WHITE);
        Category c2 = new Category("n2", Color.RED);
        Category c3 = new Category("n3", Color.BLACK);
        ArrayList<Category> givenCategories = new ArrayList<>();
        givenCategories.add(c1);
        givenCategories.add(c2);
        givenCategories.add(c3);
        categoriesModel.setCategories(givenCategories);

        // When
        Category newCategory1 = new Category("n3", Color.RED);
        Category newCategory2 = new Category("n2", Color.BLUE);
        categoriesModel.addCategory(c1);
        categoriesModel.addCategory(new Category("n2", Color.RED));
        categoriesModel.addCategory(newCategory1);
        categoriesModel.addCategory(newCategory2);
        ArrayList<Category> returnedCategories = categoriesModel.getAllCategories();

        // Then
        Assertions.assertEquals(5, returnedCategories.size());
        Assertions.assertTrue(returnedCategories.contains(c1));
        Assertions.assertTrue(returnedCategories.contains(newCategory1));
        Assertions.assertTrue(returnedCategories.contains(newCategory2));
    }

    @Test
    void updateCategoryById_should_do_not_modify_model_if_Id_is_not_present() {
        // Given
        Category c1 = new Category("n1", Color.WHITE);
        Category c2 = new Category("n2", Color.RED);
        ArrayList<Category> givenCategories = new ArrayList<>();
        givenCategories.add(c1);
        categoriesModel.setCategories(givenCategories);

        // When
        categoriesModel.updateCategoryById(c2.getId(), c1);
        categoriesModel.updateCategoryById(c2.getId(), c2);
        ArrayList<Category> returnedCategories = categoriesModel.getAllCategories();

        // Then
        Assertions.assertEquals(givenCategories, returnedCategories);
    }

    @Test
    void updateCategoryById_should_overwrite_existing_category_value() {
        // Given
        Category c1 = new Category("n1", Color.WHITE);
        Category c2 = new Category("n2", Color.RED);
        ArrayList<Category> givenCategories = new ArrayList<>();
        givenCategories.add(c1);
        givenCategories.add(c2);
        categoriesModel.setCategories(givenCategories);

        // When
        Category c3 = new Category("n1", Color.WHITE);
        c2.setName("N2");
        categoriesModel.updateCategoryById(c3.getId(), c3);
        categoriesModel.updateCategoryById(c2.getId(), c2);
        ArrayList<Category> returnedCategories = categoriesModel.getAllCategories();

        // Then
        Assertions.assertTrue(returnedCategories.contains(c1));
        Assertions.assertTrue(returnedCategories.contains(c2));
    }
}
