package com.example.homework_1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ScoreFragment extends Fragment {
    private static TicTacToe ticTacToe;

    public ScoreFragment() {
        // Required empty public constructor
    }

    public static ScoreFragment newInstance(TicTacToe ttt) {
        ticTacToe = ttt;
        Bundle args = new Bundle();
        
        ScoreFragment fragment = new ScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(ticTacToe != null) {
            if (ticTacToe.getFirstPlayerName() != null) {
                TextView firstName = view.findViewById(R.id.firstPlayerName);
                firstName.setText(ticTacToe.getFirstPlayerName());
            }

            if (ticTacToe.getSecondPlayerName() != null) {
                TextView secondName = view.findViewById(R.id.secondPlayerName);
                secondName.setText(ticTacToe.getSecondPlayerName());
            }

            TextView firstScore = view.findViewById(R.id.firstPlayerScore);
            firstScore.setText(": " + ticTacToe.getFirst_score());

            TextView secondScore = view.findViewById(R.id.secondPlayerScore);
            secondScore.setText(": " + ticTacToe.getSecond_score());
        }

    }
}