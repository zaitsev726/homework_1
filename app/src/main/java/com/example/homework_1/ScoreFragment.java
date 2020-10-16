package com.example.homework_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ScoreFragment extends Fragment {


    public ScoreFragment() {
        // Required empty public constructor
    }

    public static ScoreFragment newInstance() {

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
}