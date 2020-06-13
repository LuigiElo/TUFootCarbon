package com.example.androidapp.ui.about_us;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidapp.R;

public class AboutUsFragment extends Fragment {

    private AboutUsModel aboutUsModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutUsModel =
                ViewModelProviders.of(this).get(AboutUsModel.class);
        View root = inflater.inflate(R.layout.fragment_about_us, container, false);

        Button button_icons = root.findViewById(R.id.icons_button);
        Button api_button = root.findViewById(R.id.api_button);

        button_icons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://docs.google.com/document/d/1CcHg6rW3wOlLc_LQ3RVjI4NoWI56gLO9fqZ9FraurHI/edit?usp=sharing"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        api_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.carbonkit.net/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        return root;
    }
}