package be.thomasmore.cookbook.ui.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.cookbook.ui.models.Category;
import be.thomasmore.cookbook.ui.models.Favorite;
import be.thomasmore.cookbook.ui.models.Ingredient;
import be.thomasmore.cookbook.ui.models.Recipe;
import be.thomasmore.cookbook.ui.models.RecipeIngredient;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "recipes";

    // uitgevoerd bij instantiatie van DatabaseHelper
    // -> als database nog niet bestaat, dan creëren (call onCreate)
    // -> als de DATABASE_VERSION hoger is dan de huidige versie,
    //    dan upgraden (call onUpgrade)

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //vv Delete DB after changing table layout (adding or deleting columns) vv
        //context.deleteDatabase(DATABASE_NAME);
    }

    // methode wordt uitgevoerd als de database gecreëerd wordt
    // hierin de tables creëren en opvullen met gegevens
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_FAVORITE= "CREATE TABLE favorite (" +
                "favoriteId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "recipeId INTEGER)";
        db.execSQL(CREATE_TABLE_FAVORITE);

        String CREATE_TABLE_CATEGORY= "CREATE TABLE category (" +
                "categoryId INTEGER PRIMARY KEY," +
                "name TEXT)";
        db.execSQL(CREATE_TABLE_CATEGORY);

        String CREATE_TABLE_RECIPE = "CREATE TABLE recipe (" +
                "recipeId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "instructions TEXT," +
                "picture TEXT," +
                "categoryId INTEGER," +
                "FOREIGN KEY (categoryId) REFERENCES category(categoryId))";
        db.execSQL(CREATE_TABLE_RECIPE);

        String CREATE_TABLE_INGREDIENT = "CREATE TABLE ingredient (" +
                "ingredientId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT)";
        db.execSQL(CREATE_TABLE_INGREDIENT);

        String CREATE_TABLE_RECIPEINGREDIENT = "CREATE TABLE recipeIngredient (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "recipeId INTEGER," +
                "ingredientId INTEGER," +
                "measurement TEXT," +
                "FOREIGN KEY (ingredientId) REFERENCES ingredient(ingredientId)," +
                "FOREIGN KEY (recipeId) REFERENCES recipe(recipeId))";
        db.execSQL(CREATE_TABLE_RECIPEINGREDIENT);

        insertCategories(db);
        insertRecipes(db);
        insertIngredients(db);
        insertRecipeIngredients(db);
    }

    private void insertRecipes(SQLiteDatabase db) {
        db.execSQL("INSERT INTO recipe (name, instructions, picture, categoryId) VALUES ('Apple pie', 'Bake the pie', 'https://via.placeholder.com/150',  1);");
        db.execSQL("INSERT INTO recipe (name, instructions, picture,  categoryId) VALUES ('Spaghetti bolognese', 'Cook the pasta', 'https://via.placeholder.com/150',  2);");
    }

    private void insertCategories(SQLiteDatabase db) {
        db.execSQL("INSERT INTO category (categoryId, name) VALUES (1, 'Dessert');");
        db.execSQL("INSERT INTO category (categoryId, name) VALUES (2, 'Pasta');");
        db.execSQL("INSERT INTO category (categoryId, name) VALUES (3, 'Vegetarian');");
        db.execSQL("INSERT INTO category (categoryId, name) VALUES (4, 'Seafood');");
        db.execSQL("INSERT INTO category (categoryId, name) VALUES (5, 'Breakfast');");

    }

    private void insertIngredients(SQLiteDatabase db) {
        db.execSQL("INSERT INTO ingredient (ingredientId, name) VALUES (1, 'Apples');");
        db.execSQL("INSERT INTO ingredient (ingredientId, name) VALUES (2, 'Pie crust');");
        db.execSQL("INSERT INTO ingredient (ingredientId, name) VALUES (3, 'Ice Cream');");
        db.execSQL("INSERT INTO ingredient (ingredientId, name) VALUES (4, 'Spaghetti');");
        db.execSQL("INSERT INTO ingredient (ingredientId, name) VALUES (5, 'Tomato Sauce');");
        db.execSQL("INSERT INTO ingredient (ingredientId, name) VALUES (6, 'Parmesan');");

    }

    private void insertRecipeIngredients(SQLiteDatabase db) {
        db.execSQL("INSERT INTO recipeIngredient (ingredientId, recipeId, measurement) VALUES (1, 1, '5');");
        db.execSQL("INSERT INTO recipeIngredient (ingredientId, recipeId, measurement) VALUES (2, 1, '1 package');");
        db.execSQL("INSERT INTO recipeIngredient (ingredientId, recipeId, measurement) VALUES (3, 1, '3 scoops');");
        db.execSQL("INSERT INTO recipeIngredient (ingredientId, recipeId, measurement) VALUES (4, 2, '500gr');");
        db.execSQL("INSERT INTO recipeIngredient (ingredientId, recipeId, measurement) VALUES (5, 2, '1L');");
        db.execSQL("INSERT INTO recipeIngredient (ingredientId, recipeId, measurement) VALUES (6, 2, 'to serve');");
    }

    // methode wordt uitgevoerd als database geupgrade wordt
    // hierin de vorige tabellen wegdoen en opnieuw creëren
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS recipe");
        db.execSQL("DROP TABLE IF EXISTS recipeIngredient");
        db.execSQL("DROP TABLE IF EXISTS ingredient");
        db.execSQL("DROP TABLE IF EXISTS favorite");

        // Create tables again
        onCreate(db);
    }

    //-------------------------------------------------------------------------------------------------
    //  CRUD Operations
    //-------------------------------------------------------------------------------------------------

    // insert-methode met ContentValues
    public long insertRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", recipe.getName());
        values.put("categoryId", recipe.getCategoryId());
        values.put("instructions", recipe.getInstructions());
        values.put("picture", "");

        long id = db.insert("recipe", null, values);

        db.close();
        return id;
    }

    public long insertIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", ingredient.getName());

        long id = db.insert("ingredient", null, values);

        db.close();
        return id;
    }

    public long insertRecipeIngredient(RecipeIngredient recipeIngredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ingredientId", recipeIngredient.getIngredientId());
        values.put("recipeId", recipeIngredient.getRecipeId());
        values.put("measurement", recipeIngredient.getMeasurement());

        Log.i("findme", values + "");

        long id = db.insert("recipeIngredient", null, values);

        db.close();
        return id;
    }

    public long insertFavorite(int recipeId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("recipeId", recipeId);

        long id = db.insert("favorite", null, values);

        db.close();
        return id;
    }

    public Category getCategory(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "category",      // tabelnaam
                new String[] { "categoryId", "name"}, // kolommen
                "name = ?",  // selectie
                new String[] { String.valueOf(name) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Category category = new Category();

        if (cursor.moveToFirst()) {
            category = new Category(cursor.getLong(0),
                    cursor.getString(1));
        }
        cursor.close();
        db.close();
        return category;
    }

    public Recipe getRecipe(int recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "recipe",      // tabelnaam
                new String[] { "recipeId", "name", "instructions", "picture", "categoryId"}, // kolommen
                "recipeId = ?",  // selectie
                new String[] { String.valueOf(recipeId) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Recipe recipe = new Recipe();

        if (cursor.moveToFirst()) {
            recipe = new Recipe(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getLong(4));
        }
        cursor.close();
        db.close();
        return recipe;
    }

    public Ingredient getIngredient(long ingredientId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "ingredient",      // tabelnaam
                new String[] { "ingredientId", "name"}, // kolommen
                "ingredientId = ?",  // selectie
                new String[] { String.valueOf(ingredientId) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Ingredient ingredient = new Ingredient();

        if (cursor.moveToFirst()) {
            ingredient = new Ingredient(cursor.getInt(0),
                    cursor.getString(1));
        }
        cursor.close();
        db.close();
        return ingredient;
    }

    public boolean ingredientExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM ingredient WHERE name =?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] {name});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<Recipe> getRecipes() {
        List<Recipe> lijst = new ArrayList<Recipe>();

        String selectQuery = "SELECT  * FROM recipe ORDER BY name";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(cursor.getInt(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getLong(4));
                lijst.add(recipe);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    public List<RecipeIngredient> getRecipeIngredientsByRecipeId(int recipeId) {
        List<RecipeIngredient> lijst = new ArrayList<RecipeIngredient>();

        String selectQuery = "SELECT  * FROM recipeIngredient WHERE recipeId = " + recipeId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                RecipeIngredient recipeIngredient = new RecipeIngredient(
                        cursor.getInt(1), cursor.getInt(2), cursor.getString(3));
                lijst.add(recipeIngredient);
                Log.i("findme", recipeIngredient + "");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }
    public List<Category> getCategories() {
        List<Category> lijst = new ArrayList<Category>();

        String selectQuery = "SELECT  * FROM category";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(cursor.getLong(0),
                        cursor.getString(1));
                lijst.add(category);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    public List<Ingredient> getIngredients() {
        List<Ingredient> lijst = new ArrayList<Ingredient>();

        String selectQuery = "SELECT  * FROM ingredient";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient(cursor.getLong(0),
                        cursor.getString(1));
                lijst.add(ingredient);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    public List<Favorite> getFavorites() {
        List<Favorite> lijst = new ArrayList<Favorite>();

        String selectQuery = "SELECT  * FROM favorite";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Favorite fav = new Favorite(cursor.getInt(0),
                        cursor.getInt(1));
                lijst.add(fav);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    public List<Favorite> getFavorite(int recipeID) {
        List<Favorite> lijst = new ArrayList<Favorite>();

        String selectQuery = "SELECT  * FROM favorite WHERE recipeId = " + recipeID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Favorite fav = new Favorite(cursor.getInt(0),
                        cursor.getInt(1));
                lijst.add(fav);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // delete-methode
    public boolean deleteRecipe(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numrows = db.delete(
                "recipe",
                "recipeId = ?",
                new String[] { String.valueOf(id) });

        db.close();
        return numrows > 0;
    }
    // delete-methode
    public boolean deleteFavorite(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numrows = db.delete(
                "favorite",
                "recipeId = ?",
                new String[] { String.valueOf(id) });

        db.close();
        return numrows > 0;
    }
}
