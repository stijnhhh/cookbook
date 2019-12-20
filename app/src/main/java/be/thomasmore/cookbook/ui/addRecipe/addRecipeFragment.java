package be.thomasmore.cookbook.ui.addRecipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.helpers.DatabaseHelper;
import be.thomasmore.cookbook.ui.models.Category;
import be.thomasmore.cookbook.ui.models.Ingredient;
import be.thomasmore.cookbook.ui.models.Recipe;
import be.thomasmore.cookbook.ui.models.RecipeIngredient;

public class addRecipeFragment extends Fragment {
    private View root;
    private DatabaseHelper db;

    private AddRecipeViewModel addRecipeViewModel;

    private Button addRecipeBtn;
    private Button addIngredientBtn;
    private Spinner spinnerCategories;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Recipes");

        addRecipeViewModel =
                ViewModelProviders.of(this).get(AddRecipeViewModel.class);
        root = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        addRecipeBtn = root.findViewById(R.id.buttonAdd);
        addIngredientBtn = root.findViewById(R.id.buttonAddIngredient);

        addRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAdd_onClick();
            }
        });
        addIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientInputFields();
            }
        });

        db = new DatabaseHelper(getActivity());

        addCategoriesToSpinner();
        return root;
    }

    public void buttonAdd_onClick()
    {
        EditText editName = (EditText) root.findViewById(R.id.editRecipe);
        String name = editName.getText().toString();

        Spinner mySpinner = (Spinner) root.findViewById(R.id.categoryspinner);
        String category = mySpinner.getSelectedItem().toString();

        EditText editInstructions = (EditText) root.findViewById(R.id.editInstructions);
        String instructions = editInstructions.getText().toString();


        Category cat = db.getCategory(category);

        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setCategoryId(cat.getCategoryId());
        recipe.setInstructions(instructions);

        long recipeId = db.insertRecipe(recipe);
        TableLayout table = (TableLayout) root.findViewById(R.id.tableIngredient);
        String ingredient = "";
        long ingredientId = 0;
        String measurement = "";
        for(int i = 1, j = table.getChildCount(); i < j; i++) {
            View view = table.getChildAt(i);
            if (view instanceof TableRow) {
                TableRow row = (TableRow) view;
                View measView = row.getChildAt(0);
                if(measView instanceof EditText){
                    EditText measurementEdit = (EditText) measView;
                    measurement = measurementEdit.getText().toString();
                }
                View ingredientView = row.getChildAt(1);
                if(ingredientView instanceof  EditText) {
                    EditText ingredientEdit = (EditText) ingredientView;
                    ingredient = ingredientEdit.getText().toString();
                }
                Ingredient ingredientObject = new Ingredient();
                ingredientObject.setName(ingredient);
                ingredientId = db.insertIngredient(ingredientObject);

                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.setIngredientId(ingredientId);
                recipeIngredient.setRecipeId(recipeId);
                recipeIngredient.setMeasurement(measurement);
                Log.i("findme", recipeIngredient + "");
                db.insertRecipeIngredient(recipeIngredient);
            }
        }

        Toast.makeText(getActivity(),recipe.getName() + " added!",Toast.LENGTH_SHORT).show();
    }

    public void addIngredientInputFields(){
        TableLayout table = (TableLayout) root.findViewById(R.id.tableIngredient);

        TableRow tableRow = new TableRow(getContext());
        EditText editMeasurement = new EditText(getContext());
        EditText editIngredient = new EditText(getContext());
        editIngredient.setWidth(350);

        tableRow.addView(editMeasurement);
        tableRow.addView(editIngredient);

        table.addView(tableRow);
    }

    public void addCategoriesToSpinner(){
        spinnerCategories = (Spinner) root.findViewById(R.id.categoryspinner);
        List<String> list = new ArrayList<String>();

        List<Category> categories = db.getCategories();


        for(Category category : categories){
            list.add(category.toString());

        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(dataAdapter);
    }

}