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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.helpers.DatabaseHelper;
import be.thomasmore.cookbook.ui.models.Category;
import be.thomasmore.cookbook.ui.models.Recipe;

public class addRecipeFragment extends Fragment {
    private View root;
    private DatabaseHelper db;

    private AddRecipeViewModel addRecipeViewModel;

    private Button addRecipeBtn;
    private Button addIngredientBtn;
    private Spinner spinnerCategories;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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
        String instructions = editName.getText().toString();

        TableLayout table = (TableLayout) root.findViewById(R.id.tableIngredient);


        Category cat = db.getCategory(category);

        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setCategoryId(cat.getCategoryId());
        recipe.setInstructions(instructions);

        db.insertRecipe(recipe);
        Toast.makeText(getActivity(),db.getRecipes() + "",Toast.LENGTH_SHORT).show();
    }

    public void addIngredientInputFields(){
        TableLayout table = (TableLayout) root.findViewById(R.id.tableIngredient);

        TableRow tableRow = new TableRow(getContext());
        EditText editMeasurement = new EditText(getContext());
        EditText editIngredient = new EditText(getContext());

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