package be.thomasmore.cookbook.ui.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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
    }

    // methode wordt uitgevoerd als de database gecreëerd wordt
    // hierin de tables creëren en opvullen met gegevens
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_RECIPE = "CREATE TABLE recipe (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT)";
        db.execSQL(CREATE_TABLE_RECIPE);

        /*String CREATE_TABLE_CATEGORY= "CREATE TABLE category (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "FOREIGN KEY (partyId) REFERENCES party(id))";
        db.execSQL(CREATE_TABLE_CATEGORY);
*/
        insertRecipes(db);
    }

    private void insertRecipes(SQLiteDatabase db) {
        db.execSQL("INSERT INTO recipe (recipeId, name) VALUES (1, 'Apple pie');");
        db.execSQL("INSERT INTO recipe (recipeId, name) VALUES (2, 'Spaghetti bolognese');");
    }

    private void insertPresidents(SQLiteDatabase db) {
        db.execSQL("INSERT INTO president (name, term, partyId) VALUES ('Roosevelt, Franklin Delano', '1933-1945', 2);");
        db.execSQL("INSERT INTO president (name, term, partyId) VALUES ('Truman, Harry ', '1945-1953', 2);");
        db.execSQL("INSERT INTO president (name, term, partyId) VALUES ('Eisenhower, Dwight David', '1953-1961', 1);");
        db.execSQL("INSERT INTO president (name, term, partyId) VALUES ('Kennedy, John Fitzgerald', '1961-1963', 2);");
        db.execSQL("INSERT INTO president (name, term, partyId) VALUES ('Johnson, Lyndon Baines ', '1963-1969', 2);");
        db.execSQL("INSERT INTO president (name, term, partyId) VALUES ('Nixon, Richard Milhous ', '1969-1974', 1);");
        db.execSQL("INSERT INTO president (name, term, partyId) VALUES ('Ford, Gerald Rudolph ', '1974-1977', 1);");
        db.execSQL("INSERT INTO president (name, term, partyId) VALUES ('Carter, James Earl Jr.', '1977-1981', 2);");
        db.execSQL("INSERT INTO president (name, term, partyId) VALUES ('Reagan, Ronald Wilson', '1981-1989', 1);");
        db.execSQL("INSERT INTO president (name, term, partyId) VALUES ('Bush, George Herbert Walker ', '1989-1993', 1);");
        db.execSQL("INSERT INTO president (name, term, partyId) VALUES ('Clinton, William Jefferson ', '1993-2001', 2);");
        db.execSQL("INSERT INTO president (name, term, partyId) VALUES ('Bush, George Walker', '2001-2009', 1);");

    }

    // methode wordt uitgevoerd als database geupgrade wordt
    // hierin de vorige tabellen wegdoen en opnieuw creëren
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipe");
        //db.execSQL("DROP TABLE IF EXISTS party");

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
                        cursor.getString(1), cursor.getLong(3));
                lijst.add(recipe);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

}
