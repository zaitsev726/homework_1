package com.example.homework_1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class GameFragment extends Fragment {
    private TicTacToe ticTacToe;

    private Button[][] buttons = new Button[3][3];

    private boolean firstPlayerTurn = true;
    private boolean isGaming = true;

    private int countRound = 0;

    private String firstPlayerName = getResources().getResourceName(R.string.Player1);
    private String secondPlayerName = getResources().getResourceName(R.string.Player2);

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


        setEditTextListeners((EditText) view.findViewById(R.id.player1_name));
        setEditTextListeners((EditText) view.findViewById(R.id.player2_name));

        super.onViewCreated(view, savedInstanceState);
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
        showToast(first);
    }

    //Победа второго игрока
    private void secondPlayerWin() {
        String second = secondPlayerName + " " + getResources().getString(R.string.win);
        showToast(second);
    }

    //Показывает уведомление str на экране
    private void showToast(String str) {
        Toast toast = Toast.makeText(getContext(), str, Toast.LENGTH_LONG);
        toast.show();
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
     * @param editText
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