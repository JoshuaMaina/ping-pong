package com.joshmaina.game;

import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle{
    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed = 4;
    Ball(int ballX, int ballY, int ballWidth, int ballHeight) {
        super(ballX, ballY, ballWidth, ballHeight);
        random = new Random();
        int randomXDirection = random.nextInt(2);
        if(randomXDirection == 0 )
            randomXDirection --;
        setXDirection(randomXDirection*initialSpeed);

        int randomYDirection = random.nextInt(2);
        if(randomYDirection == 0 )
            randomYDirection --;
        setYDirection(randomYDirection*initialSpeed);
    }

    public void setXDirection(int randomXDirection){
        xVelocity = randomXDirection;
    }

    public void setYDirection(int randomYDirection){
        yVelocity = randomYDirection;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(x, y, width, height);
    }
}
