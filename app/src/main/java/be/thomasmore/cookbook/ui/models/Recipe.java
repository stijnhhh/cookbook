package be.thomasmore.cookbook.ui.models;

public class Recipe {
    private long recipeId;
    private String name;
    private long categoryId;

    public Recipe() {
    }

    public Recipe(long recipeId, String name, long categoryId) {
        this.recipeId = recipeId;
        this.name = name;
        this.categoryId = categoryId;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
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

    @Override
    public String toString(){
        return name;
    }
}
