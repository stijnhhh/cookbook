package be.thomasmore.cookbook.ui.info;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import be.thomasmore.cookbook.R;

public class InfoFragment extends Fragment {

    private InfoViewModel infoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Information");

        infoViewModel =
                ViewModelProviders.of(this).get(InfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        final TextView infoGithubLinks = root.findViewById(R.id.info_links);
        infoGithubLinks.setMovementMethod(LinkMovementMethod.getInstance());
        final TextView textView = root.findViewById(R.id.text_slideshow);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return root;
    }
}