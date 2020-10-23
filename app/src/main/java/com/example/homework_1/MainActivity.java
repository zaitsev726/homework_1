package com.example.homework_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MenuFragment.Listener {
    private final String GAME_FRAGMENT = "GAME_TAG";
    private final String SCORE_FRAGMENT = "SCORE_TAG";
    private TicTacToe ticTacToe;

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
            Fragment old = null;
            String tag = null;
            if (text.equals(getResources().getString(R.string.start))) {
                ticTacToe = new TicTacToe();
                fragment = GameFragment.newInstance(ticTacToe);
                old = getSupportFragmentManager().findFragmentByTag(GAME_FRAGMENT);
                tag = GAME_FRAGMENT;
            } else if (text.equals(getResources().getString(R.string.score))) {
                fragment = ScoreFragment.newInstance(ticTacToe);
                old = getSupportFragmentManager().findFragmentByTag(SCORE_FRAGMENT);
                tag = SCORE_FRAGMENT;
            } else
                throw new IllegalArgumentException();

            if(old != null){
                getSupportFragmentManager().beginTransaction().remove(old).commit();
            }

            ft.replace(R.id.fragment_container, fragment, tag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(tag);
            ft.commit();
        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(SecondActivity.FRAGMENT_NAME, text);
            startActivity(intent);
        }
    }


}