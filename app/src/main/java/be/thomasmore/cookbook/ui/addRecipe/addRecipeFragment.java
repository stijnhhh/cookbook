package be.thomasmore.cookbook.ui.addRecipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import be.thomasmore.cookbook.R;

public class addRecipeFragment extends Fragment {

    private AddRecipeViewModel addRecipeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addRecipeViewModel =
                ViewModelProviders.of(this).get(AddRecipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        final TextView textView = root.findViewById(R.id.text_send);

        return root;
    }
}