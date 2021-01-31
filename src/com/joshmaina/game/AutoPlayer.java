package com.joshmaina.game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

public class AutoPlayer {
    Robot r;
    // These values will be used for calculations
    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    static int PADDLE_HEIGHT;
    static int PADDLE_WIDTH;

    int keyUp;
    int keyDown;

    // Used to track the ball
    public int targetPos;
    AutoPlayer( int GAME_WIDTH, int GAME_HEIGHT, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
        {
            try {
                r = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.PADDLE_WIDTH = PADDLE_WIDTH;
        this.PADDLE_HEIGHT = PADDLE_HEIGHT;
        if (id == 1 ){
            keyUp = KeyEvent.VK_W;
            keyDown = KeyEvent.VK_S;
        }else if ( id == 2 ){
            keyUp = KeyEvent.VK_UP;
            keyDown = KeyEvent.VK_DOWN;
        }

    }

    public void move(int targetPos, int paddleY){
        this.targetPos = targetPos;
        if (this.targetPos > paddleY && this.targetPos < PADDLE_HEIGHT + paddleY){
            r.keyRelease(keyUp);
            r.keyRelease(keyDown);
        }
        if (this.targetPos <= paddleY + PADDLE_HEIGHT / 2) {
            r.keyPress(keyUp);
            r.keyRelease(keyDown);
        }
        if (this.targetPos >= paddleY + PADDLE_HEIGHT / 2) {
            r.keyPress(keyDown);
            r.keyRelease(keyUp);
        }
    }



}
