package com.example.homework_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {
    public static final String FRAGMENT_NAME = "Fragment_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Fragment fragment = null;
        String fragmentName = (String) getIntent().getExtras().get(FRAGMENT_NAME);
        if (fragmentName.equals(getResources().getString(R.string.start))) {
            fragment = GameFragment.newInstance();
        } else if (fragmentName.equals(getResources().getString(R.string.score))){
            fragment = ScoreFragment.newInstance();
        } else
            throw new IllegalArgumentException();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_conrainer, fragment)
               // .addToBackStack(null)
                .commit();
    }


}