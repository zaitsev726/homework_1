package com.example.homework_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MenuFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public void itemClicked(String text) {
        View container = findViewById(R.id.fragment_container);
        if (container != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment = null;
            if(text.equals(getResources().getString(R.string.start))){
                fragment = GameFragment.newInstance();
            }else if (text.equals(getResources().getString(R.string.score))){
                fragment = ScoreFragment.newInstance();
            }else
                throw new IllegalArgumentException();
            ft.replace(R.id.fragment_container, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(SecondActivity.FRAGMENT_NAME, text);
            startActivity(intent);
        }
    }
}