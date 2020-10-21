package com.example.homework_1;

import android.graphics.Bitmap;
import android.widget.Button;

//логика игры
public class TicTacToe {
    private boolean firstPlayerTurn;
    private boolean isGaming;

    private int countRound;

    private String firstPlayerName;
    private String secondPlayerName;

    private Bitmap firstPlayerIcon;
    private Bitmap secondPlayerIcon;

    private int first_score = 0;
    private int second_score = 0;

    private String[][] gameField;

    public TicTacToe() {
        isGaming = true;
        firstPlayerTurn = true;
        countRound = 0;
        gameField = null;
    }

    public boolean checkForWin(String[][] field) {

        //проверяем все строки
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        //проверяем все столбцы
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        //проверяем диаогональ слева направо
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        //проверяем диагональ справа налево
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    public void reset() {
        countRound = 0;
        isGaming = true;
        firstPlayerTurn = true;
        gameField = null;
    }

    public String getTurn() {
        countRound++;
        if (firstPlayerTurn) {
            return  "X";
        } else
            return "O";
    }

    public void saveFieldInstance(String[][] field) {
        this.gameField = field;
    }

    public void firstPlayerWin(){
        first_score++;
        isGaming = false;
    }

    public void secondPlayerWin() {
        second_score++;
        isGaming = false;
    }

    public int getFirst_score() {
        return first_score;
    }

    public int getSecond_score() {
        return second_score;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public void setFirstPlayerName(String firstPlayerName) {
        this.firstPlayerName = firstPlayerName;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public void setSecondPlayerName(String secondPlayerName) {
        this.secondPlayerName = secondPlayerName;
    }

    public boolean isGaming() {
        return isGaming;
    }

    public String[][] getGameField() {
        return gameField;
    }

    public Bitmap getFirstPlayerIcon() {
        return firstPlayerIcon;
    }

    public void setFirstPlayerIcon(Bitmap firstPlayerIcon) {
        this.firstPlayerIcon = firstPlayerIcon;
    }

    public Bitmap getSecondPlayerIcon() {
        return secondPlayerIcon;
    }

    public void setSecondPlayerIcon(Bitmap secondPlayerIcon) {
        this.secondPlayerIcon = secondPlayerIcon;
    }

    public int getCountRound() {
        return countRound;
    }

    public void draw() {
        isGaming = false;
    }

    public boolean isFirstPlayerTurn() {
        return firstPlayerTurn;
    }

    public void changeTurn() {
        firstPlayerTurn = !firstPlayerTurn;
    }
}
