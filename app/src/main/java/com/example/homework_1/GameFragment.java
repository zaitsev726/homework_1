package com.example.homework_1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class GameFragment extends Fragment {
    private TicTacToe ticTacToe;

    private Button[][] buttons = new Button[3][3];

    private boolean firstPlayerTurn = true;
    private boolean isGaming = true;

    private int countRound = 0;

    private TextView player1;
    private TextView player2;

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance() {
        Bundle args = new Bundle();
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ticTacToe = new TicTacToe();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", "com.example.homework_1");
                buttons[i][j] = view.findViewById(resID);
                addListener(buttons[i][j]);
            }
        }

        Button resetButton = view.findViewById(R.id.button_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void addListener(@NonNull final Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGaming) {
                    if (!button.getText().equals("")) {
                        return;
                    }

                    if (firstPlayerTurn) {
                        button.setText("X");
                    } else
                        button.setText("O");

                    countRound++;

                    String[][] field = new String[3][3];
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            field[i][j] = buttons[i][j].getText().toString();
                        }
                    }


                    if (ticTacToe.checkForWin(field)) {
                        if (firstPlayerTurn) {
                            firstPlayerWin();
                            isGaming = false;
                        } else {
                            secondPlayerWin();
                            isGaming = false;
                        }
                    } else if (countRound == 9) {
                        draw();
                        isGaming = false;
                    } else {
                        firstPlayerTurn = !firstPlayerTurn;
                    }
                }else {
                    String resetGame = getResources().getString(R.string.endGame);
                    showToast(resetGame);
                }
            }
        });
    }

    private void draw() {
        String draw = getResources().getString(R.string.draw);
        showToast(draw);
    }

    private void firstPlayerWin() {
        String first = getResources().getString(R.string.firstWin);
        showToast(first);
    }

    private void secondPlayerWin() {
        String second = getResources().getString(R.string.secondWin);
        showToast(second);
    }

    private void showToast(String str){
        Toast toast = Toast.makeText(getContext(), str, Toast.LENGTH_LONG);
        toast.show();
    }

    private void reset(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        countRound = 0;
        isGaming = true;
        firstPlayerTurn = true;
    }
}