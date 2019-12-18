package be.thomasmore.cookbook.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.helpers.HttpReader;
import be.thomasmore.cookbook.ui.helpers.JsonHelper;
import be.thomasmore.cookbook.ui.models.Category;

public class RecipeFragment extends Fragment {

    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recipe, container, false);
         textView = root.findViewById(R.id.text_gallery);

        readCategories();
        return root;
    }

    private void readCategories()
    {

        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                List<Category> categories = jsonHelper.getCategories(result);

                String tekst = "";
                for (int i = 0; i < categories.size(); i++) {
                    tekst += categories.get(i).getName() + " - ";
                }
                textView.setText(tekst);
            }
        });

        httpReader.execute("https://www.themealdb.com/api/json/v1/1/list.php?c=list");
    }
}