package be.thomasmore.cookbook.ui.detail;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import be.thomasmore.cookbook.R;
import be.thomasmore.cookbook.ui.detail.DetailFragment;


public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("findme", "being created");
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new DetailFragment ()).commit();}
    }
}
