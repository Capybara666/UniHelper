package com.github.UniHelper.model.categories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.UniHelper.model.utils.ColorJsonDeserializer;
import com.github.UniHelper.model.utils.ColorJsonSerializer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

public class DefaultCategoriesModel implements CategoriesModel {

    private static volatile DefaultCategoriesModel instance;

    private final String saveFileName;
    private final ObjectMapper categoryMapper;
    private ArrayList<Category> categories;

    public static DefaultCategoriesModel getInstance() {
        DefaultCategoriesModel result = instance;
        if (result != null) {
            return result;
        }
        synchronized (DefaultCategoriesModel.class) {
            if (instance == null) {
                instance = new DefaultCategoriesModel();
            }
            return instance;
        }
    }

    @Override
    public void addCategory(Category category) {
        if (!categories.contains(category)) {
            categories.add(category);
            save();
        }
    }

    @Override
    public void updateCategoryById(UUID id, Category category) {
        Category categoryWithSameId = categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (categoryWithSameId != null) {
            categoryWithSameId.setName(category.getName());
            categoryWithSameId.setColor(category.getColor());
        }
    }

    @Override
    public void deleteCategory(Category category) {
        categories.remove(category);
        save();
    }

    @Override
    public ArrayList<Category> getAllCategories() {
        return getDeepCategoriesCopy(categories);
    }

    @Override
    public void setCategories(ArrayList<Category> categories) {
        this.categories = getDeepCategoriesCopy(categories);
        save();
    }

    private DefaultCategoriesModel() {
        saveFileName = "saved_categories.txt";
        categoryMapper = new ObjectMapper();
        categories = new ArrayList<>();
        addSerializersToMapper();
        load();
    }

    private void addSerializersToMapper() {
        SimpleModule awtModule = new SimpleModule("AWT Module");
        awtModule.addSerializer(Color.class, new ColorJsonSerializer());
        awtModule.addDeserializer(Color.class, new ColorJsonDeserializer());
        categoryMapper.registerModule(awtModule);
    }

    private void load() {
        try {
            categories = categoryMapper.readValue(new File(saveFileName), new TypeReference<>() {});
        } catch (IOException e) {
            createNewSaveFile();
        }
    }

    private void save() {
        try (PrintWriter out = new PrintWriter(saveFileName, StandardCharsets.UTF_8)) {
            ObjectWriter writer = categoryMapper.writer().withDefaultPrettyPrinter();
            String json = writer.writeValueAsString(categories);
            out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Category> getDeepCategoriesCopy(ArrayList<Category> originalCategories) {
        ArrayList<Category> deepCopy;
        try {
            deepCopy = categoryMapper
                    .readValue(categoryMapper.writeValueAsString(originalCategories), new TypeReference<>() {
                    });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return deepCopy;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createNewSaveFile() {
        try {
            File f = new File(saveFileName);
            f.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
