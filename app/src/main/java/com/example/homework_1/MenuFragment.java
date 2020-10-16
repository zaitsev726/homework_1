package com.example.homework_1;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment {
    static interface Listener{
        void itemClicked(String text);
    }

    private Listener listener;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Listener)
            this.listener = (Listener) context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Button startButton = view.findViewById(R.id.start_game);
        addListener(startButton);

        Button scoreButton = view.findViewById(R.id.score);
        addListener(scoreButton);

        super.onViewCreated(view, savedInstanceState);
    }

    private void addListener(@NonNull final Button button){
       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemClicked(button.getText().toString());
            }
        });
    }
}