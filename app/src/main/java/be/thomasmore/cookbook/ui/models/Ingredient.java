package be.thomasmore.cookbook.ui.models;

public class Ingredient {
    private long ingredientId;
    private String name;

    public Ingredient() {
    }

    public Ingredient(long ingredientId, String name) {
        this.ingredientId = ingredientId;
        this.name = name;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
