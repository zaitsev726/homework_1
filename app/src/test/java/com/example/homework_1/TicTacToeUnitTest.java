package com.example.homework_1;

import org.junit.Test;

import static org.junit.Assert.*;

public class TicTacToeUnitTest {
    private TicTacToe ticTacToe = new TicTacToe();
    @Test
    public void firstPlayerWins() {
        String[][] str1 = {
                {"", "", ""},
                {"X", "X", "X"},
                {"O", "O", ""}
        };
        assertTrue(ticTacToe.checkForWin(str1));

        String[][] str2 = {
                {"X", "O", "O"},
                {"X", "X", ""},
                {"X", "O", ""}
        };
        assertTrue(ticTacToe.checkForWin(str2));


        String[][] str3 = {
                {"X", "O", "X"},
                {"O", "X", "O"},
                {"X", "O", ""}
        };
        assertTrue(ticTacToe.checkForWin(str3));


        String[][] str4 = {
                {"O", "X", ""},
                {"X", "X", "X"},
                {"O", "O", ""}
        };
        assertTrue(ticTacToe.checkForWin(str4));


        String[][] str5 = {
                {"", "O", "X"},
                {"", "O", "X"},
                {"X", "O", "X"}
        };
        assertTrue(ticTacToe.checkForWin(str5));
    }

    @Test
    public void secondPlayerWins() {
        String[][] str1 = {
                {"", "X", "X"},
                {"X", "X", "O"},
                {"O", "O", "O"}
        };
        assertTrue(ticTacToe.checkForWin(str1));

        String[][] str2 = {
                {"O", "X", ""},
                {"X", "O", "X"},
                {"", "", "O"}
        };
        assertTrue(ticTacToe.checkForWin(str2));


        String[][] str3 = {
                {"X", "O", "X"},
                {"X", "O", ""},
                {"", "O", ""}
        };
        assertTrue(ticTacToe.checkForWin(str3));


        String[][] str4 = {
                {"O", "X", "O"},
                {"X", "O", ""},
                {"O", "X", "X"}
        };
        assertTrue(ticTacToe.checkForWin(str4));


        String[][] str5 = {
                {"X", "", "X"},
                {"O", "O", "O"},
                {"X", "", ""}
        };
        assertTrue(ticTacToe.checkForWin(str5));
    }

    @Test
    public void gameNotFinished() {
        String[][] str1 = {
                {"", "", ""},
                {"", "", ""},
                {"", "", ""}
        };
        assertFalse(ticTacToe.checkForWin(str1));

        String[][] str2 = {
                {"X", "O", "O"},
                {"X", "X", ""},
                {"O", "X", ""}
        };
        assertFalse(ticTacToe.checkForWin(str2));


        String[][] str3 = {
                {"X", "O", "O"},
                {"O", "X", "X"},
                {"X", "O", "X"}
        };
        assertFalse(ticTacToe.checkForWin(str3));


        String[][] str4 = {
                {"O", "X", "O"},
                {"X", "X", "X"},
                {"X", "O", "O"}
        };
        assertFalse(ticTacToe.checkForWin(str4));


        String[][] str5 = {
                {"", "O", "X"},
                {"", "", ""},
                {"X", "O", "X"}
        };
        assertFalse(ticTacToe.checkForWin(str5));
    }
}