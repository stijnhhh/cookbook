package be.thomasmore.cookbook.ui.models;

public class Recipe {
    private int recipeId;
    private String name;
    private String instructions;
    private String picture;
    private long categoryId;

    public Recipe() {
    }

    public Recipe(int recipeId, String name, String instructions, String picture, long categoryId) {
        this.recipeId = recipeId;
        this.name = name;
        this.instructions = instructions;
        this.picture = picture;
        this.categoryId = categoryId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
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

    @Override
    public String toString(){
        return name;
    }
}
