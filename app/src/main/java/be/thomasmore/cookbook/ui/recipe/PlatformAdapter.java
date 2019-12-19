package be.thomasmore.cookbook.ui.recipe;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.helpers.DatabaseHelper;
import be.thomasmore.cookbook.ui.helpers.DownloadImageTask;
import be.thomasmore.cookbook.ui.models.Favorite;
import be.thomasmore.cookbook.ui.models.RecipeAPI;

public class PlatformAdapter extends ArrayAdapter<RecipeAPI> {
    private final Context context;
    private final List<RecipeAPI> values;

    public PlatformAdapter(Context context, List<RecipeAPI> values) {
        super(context, R.layout.platformlistviewitem, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.platformlistviewitem, parent, false);

        final TextView textName = (TextView) rowView.findViewById(R.id.name);
        final ImageView mealImage = (ImageView) rowView.findViewById(R.id.meal_image);
        final Button addToFavoriteButton = (Button) rowView.findViewById(R.id.add_to_favorite);

        textName.setText(values.get(position).getName());

        new DownloadImageTask(mealImage).execute(values.get(position).getPicture());
        mealImage.getLayoutParams().width = 200;

        addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(getContext());
                int recipeID =  values.get(position).getRecipeId();
                String recipe = values.get(position).getName();

                if(db.getFavorite(recipeID).size() == 0)
                {
                    db.insertFavorite(recipeID);

                    toon(recipe + " is added to favorites!!");
                } else
                {
                    Log.d("lol", "" + db.getFavorite(recipeID).size());
                    toon(recipe + " is already added!!");
                }
            }
        });

        return rowView;
    }

    public void toon(String tekst)
    {
        Toast.makeText(getContext(),tekst ,Toast.LENGTH_SHORT).show();
    }
}
