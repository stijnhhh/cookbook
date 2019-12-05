package be.thomasmore.cookbook.ui.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.cookbook.ui.models.Category;
import be.thomasmore.cookbook.ui.models.Recipe;

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
        context.deleteDatabase(DATABASE_NAME);
    }

    // methode wordt uitgevoerd als de database gecreëerd wordt
    // hierin de tables creëren en opvullen met gegevens
    @Override
    public void onCreate(SQLiteDatabase db) {

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
        values.put("categoryId", 1);

        long id = db.insert("recipe", null, values);

        db.close();
        return id;
    }

    public List<Recipe> getRecipes() {
        List<Recipe> lijst = new ArrayList<Recipe>();

        String selectQuery = "SELECT  * FROM recipe ORDER BY name";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(cursor.getLong(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getLong(4));
                lijst.add(recipe);
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
}
