package com.joshmaina.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable{

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * 0.5555);
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    AutoPlayer auto1;
    AutoPlayer auto2;
    Tracker myTracker;
    boolean isPlayer1Computer;
    boolean isPlayer2Computer;

    GamePanel(boolean isPlayer1Computer, boolean isPlayer2Computer) {
        newPaddles();
        newBall();
        myTracker = new Tracker(GAME_WIDTH, GAME_HEIGHT, BALL_DIAMETER, PADDLE_WIDTH);
        this.isPlayer1Computer = isPlayer1Computer;
        this.isPlayer2Computer = isPlayer2Computer;
        if(this.isPlayer1Computer)
            auto1 = new AutoPlayer(GAME_WIDTH, GAME_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT,1 );
        if(this.isPlayer2Computer)
            auto2 = new AutoPlayer(GAME_WIDTH, GAME_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT, 2);
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);
        gameThread = new Thread(this);
        gameThread.start();
        System.out.println(gameThread.getName());


    }

    public void newBall(){
        random = new Random();
        ball = new Ball(GAME_WIDTH / 2 - BALL_DIAMETER / 2,
                random.nextInt(GAME_HEIGHT-BALL_DIAMETER),
                BALL_DIAMETER, BALL_DIAMETER);
    }
    public void newPaddles(){
        paddle1 = new Paddle(0,(GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2),
                PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH,(GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2),
                PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }
    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();
        if (this.isPlayer1Computer)
            auto1.move(myTracker.getY() == 0 || myTracker.getX() == GAME_WIDTH - BALL_DIAMETER - PADDLE_WIDTH? ball.y : myTracker.getY(), paddle1.y);
        if (this.isPlayer2Computer)
            auto2.move(myTracker.getY() == 0 || myTracker.getX() == PADDLE_WIDTH ? ball.y : myTracker.getY(), paddle2.y);
    }

    public void checkCollision(){
        //stops paddles at window edges
        if (paddle1.y <= 0 )
            paddle1.y = 0;
        if (paddle2.y <= 0 )
            paddle2.y = 0;
        if (paddle1.y >= GAME_HEIGHT-PADDLE_HEIGHT)
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
        if (paddle2.y >= GAME_HEIGHT-PADDLE_HEIGHT)
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;

        // bounce ball off top & window edges
        if (ball.y <= 0 || ball.y >= GAME_HEIGHT - BALL_DIAMETER){
            ball.setYDirection(-ball.yVelocity);
        }

        // bounces ball off paddles
        if (ball.intersects(paddle1) || ball.intersects(paddle2)) {
            ball.xVelocity *= -1;
            ball.xVelocity += (ball.xVelocity > 0 ? 1 : -1 ); // optional increase in speed
            ball.yVelocity += (ball.yVelocity > 0 ? 1 : -1 ); // optional increase in speed
            myTracker.track(ball.x, ball.y, ball.xVelocity, ball.yVelocity);
            // System.out.println("Target Position : X : " + myTracker.getX() + " Y: " + myTracker.getY());
        }

        // Give player 1 point and create new paddles and ball
        if (ball.x <= 0){
            score.player2 ++;
            newPaddles();
            newBall();
            System.out.println(score);
            myTracker.track(ball.x, ball.y, ball.xVelocity, ball.yVelocity);
        }
        if (ball.x >= GAME_WIDTH){
            score.player1 ++;
            newPaddles();
            newBall();
            System.out.println(score);
            myTracker.track(ball.x, ball.y, ball.xVelocity, ball.yVelocity);
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if(delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }

    }

    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);

        }
    }
}
