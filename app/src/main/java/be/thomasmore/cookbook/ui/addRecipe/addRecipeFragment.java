package be.thomasmore.cookbook.ui.addRecipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.helpers.DatabaseHelper;
import be.thomasmore.cookbook.ui.models.Recipe;

public class addRecipeFragment extends Fragment {
    private View root;
    private DatabaseHelper db;

    private AddRecipeViewModel addRecipeViewModel;

    private Button addRecipeBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        addRecipeViewModel =
                ViewModelProviders.of(this).get(AddRecipeViewModel.class);
        root = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        addRecipeBtn = root.findViewById(R.id.buttonAdd);

        addRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAdd_onClick();
            }
        });

        db = new DatabaseHelper(getActivity());
        return root;
    }

    public void buttonAdd_onClick()
    {
        EditText editName = (EditText) root.findViewById(R.id.editRecipe);
        String name = editName.getText().toString();

        Recipe recipe = new Recipe();
        recipe.setName(name);

        db.insertRecipe(recipe);
        Toast.makeText(getActivity(),db.getRecipes() + "",Toast.LENGTH_SHORT).show();
    }


}