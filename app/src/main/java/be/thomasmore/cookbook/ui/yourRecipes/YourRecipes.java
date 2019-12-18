package be.thomasmore.cookbook.ui.yourRecipes;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.detail.CustomListview;
import be.thomasmore.cookbook.ui.detail.DetailCustomRecipe;
import be.thomasmore.cookbook.ui.detail.DetailFragment;
import be.thomasmore.cookbook.ui.favorite.FavoriteViewModel;
import be.thomasmore.cookbook.ui.helpers.DatabaseHelper;
import be.thomasmore.cookbook.ui.models.Favorite;
import be.thomasmore.cookbook.ui.models.Recipe;
import be.thomasmore.cookbook.ui.models.RecipeAPI;

public class YourRecipes extends Fragment {

    private YourRecipesViewModel mViewModel;
    private View root;
    private DatabaseHelper db;
    private List<Recipe> recipes;

    public static YourRecipes newInstance() {
        return new YourRecipes();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(YourRecipesViewModel.class);
        root = inflater.inflate(R.layout.your_recipes_fragment, container, false);

        CustomListview customListview = (CustomListview) root.findViewById(R.id.customListYourRecipes);
        db = new DatabaseHelper(getActivity());
        recipes = db.getRecipes();

        final ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(getContext(),
                android.R.layout.simple_list_item_1, recipes);


        final ListView listViewFavs = (ListView) root.findViewById(R.id.customListYourRecipes);
        listViewFavs.setAdapter(adapter);
        listViewFavs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView,
                                    View childView, int position, long id) {
                onClick(recipes.get(position).getRecipeId());
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(YourRecipesViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onClick(int recipeId){
        Fragment fragment = new DetailCustomRecipe();
        Bundle args = new Bundle();
        args.putInt("id", recipeId);
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
