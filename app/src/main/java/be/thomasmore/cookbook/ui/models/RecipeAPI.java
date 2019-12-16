package be.thomasmore.cookbook.ui.models;

import java.util.List;

public class RecipeAPI {
    private String name;
    private String instructions;
    private String picture;
    private String category;
    private List<String> ingredients;
    private List<String> measurements;

    public RecipeAPI() {
    }

    public RecipeAPI(String name, String instructions, String picture, String category, List<String> ingredients, List<String> measurements) {
        this.name = name;
        this.instructions = instructions;
        this.picture = picture;
        this.category = category;
        this.ingredients = ingredients;
        this.measurements = measurements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<String> measurements) {
        this.measurements = measurements;
    }
}
