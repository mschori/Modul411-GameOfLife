package com.codebind;
import javax.swing.*;


public class GameOfLife extends JFrame {

    public static void main(String[] args) {

        // Create new board
        Board board = new Board();

        // Create window-frame and add created board
        JFrame frame = new JFrame("Game of life");
        frame.setContentPane(board.panelMain);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
