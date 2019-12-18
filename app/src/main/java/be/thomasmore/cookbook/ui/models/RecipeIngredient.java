package be.thomasmore.cookbook.ui.models;

public class RecipeIngredient {
    private long ingredientId;
    private long recipeId;
    private String measurement;

    public RecipeIngredient() {
    }

    public RecipeIngredient(long ingredientId, long recipeId, String measurement) {
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
        this.measurement = measurement;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString(){
        return measurement + " Recipe: " + recipeId + " Ingredient: " + ingredientId;
    }
}
