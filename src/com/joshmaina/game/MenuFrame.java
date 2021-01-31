package com.joshmaina.game;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {

    JPanel mainQuestionPanel;
    JButton button;
    MenuFrame(){

        JPanel player1Panel = new JPanel();
        JPanel player2Panel = new JPanel();
        JLabel player1Text = new JLabel("Player 1 :");
        // player1Text.setHorizontalAlignment(JLabel.LEFT);
        JLabel player2Text = new JLabel("Player 2 :");
        // player2Text.setHorizontalAlignment(JLabel.LEFT);

        JRadioButton player1Computer = new JRadioButton("Computer", true);
        player1Computer.setFocusable(false);
        JRadioButton player1Human = new JRadioButton("Human");
        player1Human.setFocusable(false);
        ButtonGroup player1 = new ButtonGroup();
        player1.add(player1Computer);
        player1.add(player1Human);

        JRadioButton player2Computer = new JRadioButton("Computer", true);
        player2Computer.setFocusable(false);
        JRadioButton player2Human = new JRadioButton("Human");
        player2Human.setFocusable(false);
        ButtonGroup player2 = new ButtonGroup();
        player2.add(player2Computer);
        player2.add(player2Human);

        player1Panel.setBounds(0, 100, 500, 40);
        player1Panel.setLayout(new FlowLayout(0));
        player1Panel.setBackground(new Color(186, 186, 186));
        player1Panel.add(player1Text);
        player1Panel.add(player1Computer);
        player1Panel.add(player1Human);

        player2Panel.setBounds(0, 160, 500, 40);
        player2Panel.setLayout(new FlowLayout(0));
        player2Panel.setBackground(new Color(186, 186, 186));
        player2Panel.add(player2Text);
        player2Panel.add(player2Computer);
        player2Panel.add(player2Human);

        mainQuestionPanel = new JPanel();
        mainQuestionPanel.setBounds(0, 0, 500, 400);
        mainQuestionPanel.setPreferredSize(new Dimension(500, 400));
        mainQuestionPanel.setBackground(Color.white);
        mainQuestionPanel.setLayout(null);

        mainQuestionPanel.add(player1Panel);
        mainQuestionPanel.add(player2Panel);

        button = new JButton("Click to Begin");
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "Player1 will use W - S keys and Player 2 will use UP and DOWN Keys",
                    "Game Play",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new GameFrame(player1Computer.isSelected(), player2Computer.isSelected());
        });
        button.setFocusable(false);
        button.setBounds(150, 250, 200, 40);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setSize(500,400);
        // this.setLayout(null);
        this.add(button);
        this.add(mainQuestionPanel);
        this.pack();
        this.setVisible(true);
    }


}
