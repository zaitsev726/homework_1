package com.example.homework_1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class GameFragment extends Fragment {
    private final String idFirstScore = "FIRST_SCORE";
    private final String idSecondScore = "SECOND_SCORE";
    private final String idButton = "BUTTON_";
    private final String idTurn = "PLAYER_TURN";

    private TicTacToe ticTacToe;

    private Button[][] buttons = new Button[3][3];

    private boolean firstPlayerTurn = true;
    private boolean isGaming = true;

    private int countRound = 0;

    private String firstPlayerName;
    private String secondPlayerName;

    private TextView firstPlayerScore;
    private TextView secondPlayerScore;

    private int first_score = 0;
    private int second_score = 0;


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
        firstPlayerName = getResources().getString(R.string.Player1);
        secondPlayerName = getResources().getString(R.string.Player2);

        firstPlayerScore = view.findViewById(R.id.first_score);
        secondPlayerScore = view.findViewById(R.id.second_score);
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


        setEditTextListeners((EditText) view.findViewById(R.id.player1_name));
        setEditTextListeners((EditText) view.findViewById(R.id.player2_name));

        if(savedInstanceState != null){
            first_score = savedInstanceState.getInt(idFirstScore);
            addingScore(firstPlayerScore, first_score);
            second_score = savedInstanceState.getInt(idSecondScore);
            addingScore(secondPlayerScore, second_score);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    String buttonID = idButton + i + j;
                    buttons[i][j].setText(savedInstanceState.getString(buttonID));
                }
            }
            firstPlayerTurn = savedInstanceState.getBoolean(idTurn);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(idFirstScore, first_score);
        outState.putInt(idSecondScore, second_score);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = idButton + i + j;
                outState.putString(buttonID, String.valueOf(buttons[i][j].getText()));
            }
        }
        outState.putBoolean(idTurn, firstPlayerTurn);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private void addListener(@NonNull final Button button) {
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
                } else {
                    String resetGame = getResources().getString(R.string.endGame);
                    showToast(resetGame);
                }
            }
        });
    }

    //Ничья
    private void draw() {
        String draw = getResources().getString(R.string.draw);
        showToast(draw);
    }

    //Победа первого игрока
    private void firstPlayerWin() {
        String first = firstPlayerName + " " + getResources().getString(R.string.win);
        first_score ++;
        addingScore(firstPlayerScore, first_score);
        showToast(first);
    }

    //Победа второго игрока
    private void secondPlayerWin() {
        String second = secondPlayerName + " " + getResources().getString(R.string.win);
        second_score ++;
        addingScore(secondPlayerScore, second_score);
        showToast(second);
    }

    //Показывает уведомление str на экране
    private void showToast(String str) {
        Toast toast = Toast.makeText(getContext(), str, Toast.LENGTH_LONG);
        toast.show();
    }

    //Обновляет счет
    @SuppressLint("SetTextI18n")
    private void addingScore(TextView view, int score){
        view.setText(": " + score);
    }
    //Перезапуск игры
    private void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        countRound = 0;
        isGaming = true;
        firstPlayerTurn = true;
    }

    /**
     * Устанавливаем 2 listener на editText
     * Первый, чтобы при нажатии Enter или Down убирался курсор
     * Второй, чтобы при нажатии на текст появлялся курсор
     * @param editText текстовое поле
     */
    private void setEditTextListeners(@NonNull final EditText editText){
        editText.setOnKeyListener(new View.OnKeyListener() {
                                      public boolean onKey(View v, int keyCode, KeyEvent event) {
                                          if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                                  (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                              String playerName = editText.getText().toString();
                                              if(editText.getId() == R.id.player1_name){
                                                  firstPlayerName = playerName;
                                              } else if (editText.getId() == R.id.player2_name){
                                                  secondPlayerName = playerName;
                                              } else
                                                  throw new NullPointerException();

                                              final InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity())
                                                      .getApplicationContext()
                                                      .getSystemService(Context.INPUT_METHOD_SERVICE);

                                              imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                              editText.setCursorVisible(false);
                                              return true;
                                          }
                                          return false;
                                      }
                                  }
        );
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setCursorVisible(true);
            }
        });
    }
}