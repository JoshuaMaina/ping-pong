package com.joshmaina.game;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    GamePanel gp;

    GameFrame(boolean isPlayer1Computer, boolean isPlayer2Computer) {

        gp = new GamePanel(isPlayer1Computer, isPlayer2Computer);
        this.add(gp);
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Window adjusts to fit the content
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
