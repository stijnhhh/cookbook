package be.thomasmore.cookbook.ui.models;

public class Favorite {
    private int favoriteId;
    private int recipeId;

    public Favorite() {
    }

    public Favorite(int favoriteId, int recipeId) {
        this.favoriteId = favoriteId;
        this.recipeId = recipeId;
    }

    public Favorite(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
