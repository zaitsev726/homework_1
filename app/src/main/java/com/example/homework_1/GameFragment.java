package com.example.homework_1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class GameFragment extends Fragment {
    private final String idFirstScore = "FIRST_SCORE";
    private final String idSecondScore = "SECOND_SCORE";
    private final String idButton = "BUTTON_";
    private final String idTurn = "PLAYER_TURN";
    private final String idFirstIcon = "FIRST_ICON";
    private final String idSecondIcon = "SECOND_ICON";
    private final int PICK_IMAGE = 1;

    private int imageViewId = 0;

    private TicTacToe ticTacToe;

    private Button[][] buttons = new Button[3][3];

    private boolean firstPlayerTurn = true;
    private boolean isGaming = true;

    private int countRound = 0;

    private String firstPlayerName;
    private String secondPlayerName;

    private TextView firstPlayerScore;
    private TextView secondPlayerScore;

    private ImageView firstIconView;
    private ImageView secondIconView;


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

        firstIconView = view.findViewById(R.id.first_icon);
        secondIconView = view.findViewById(R.id.second_icon);
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

        setImageViewListener(firstIconView);
        setImageViewListener(secondIconView);

        if (savedInstanceState != null) {
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

            Bitmap bitmap1 = savedInstanceState.getParcelable(idFirstIcon);
            firstIconView.setImageBitmap(bitmap1);

            Bitmap bitmap2 = savedInstanceState.getParcelable(idSecondIcon);
            secondIconView.setImageBitmap(bitmap2);
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

        BitmapDrawable drawable1 = (BitmapDrawable) firstIconView.getDrawable();
        Bitmap bitmap1 = drawable1.getBitmap();
        outState.putParcelable(idFirstIcon, bitmap1);

        BitmapDrawable drawable2 = (BitmapDrawable) secondIconView.getDrawable();
        Bitmap bitmap2 = drawable2.getBitmap();
        outState.putParcelable(idSecondIcon, bitmap2);

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
        first_score++;
        addingScore(firstPlayerScore, first_score);
        showToast(first);
    }

    //Победа второго игрока
    private void secondPlayerWin() {
        String second = secondPlayerName + " " + getResources().getString(R.string.win);
        second_score++;
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
    private void addingScore(TextView view, int score) {
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
     *
     * @param editText текстовое поле
     */
    private void setEditTextListeners(@NonNull final EditText editText) {
        editText.setOnKeyListener(new View.OnKeyListener() {
                                      public boolean onKey(View v, int keyCode, KeyEvent event) {
                                          if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                                  (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                              String playerName = editText.getText().toString();
                                              if (editText.getId() == R.id.player1_name) {
                                                  firstPlayerName = playerName;
                                              } else if (editText.getId() == R.id.player2_name) {
                                                  secondPlayerName = playerName;
                                              } else
                                                  throw new NullPointerException();

                                              final InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity())
                                                      .getApplicationContext()
                                                      .getSystemService(Context.INPUT_METHOD_SERVICE);

                                              imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                              editText.clearFocus();
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

    private void setImageViewListener(@NonNull final ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageView.getId() == R.id.first_icon){
                    imageViewId = 1;
                }else if (imageView.getId() == R.id.second_icon){
                    imageViewId = 2;
                }else
                    throw new NullPointerException();
                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                Intent result = Intent.createChooser(photoIntent, getResources().getString(R.string.chose));
                startActivityForResult(result, PICK_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    assert data != null;
                    final Uri imageUri = data.getData();
                    assert imageUri != null;
                    final InputStream imageStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    if (imageViewId == 1) {
                        firstIconView.setImageBitmap(selectedImage);
                    } else if (imageViewId == 2) {
                        secondIconView.setImageBitmap(selectedImage);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}