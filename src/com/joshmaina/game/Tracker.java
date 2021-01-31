package com.joshmaina.game;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Tracker {
    final int GAME_WIDTH;
    final int GAME_HEIGHT;
    final int BALL_DIAMETER;
    final int PADDLE_WIDTH;
    private ArrayList<Point> futurePos; // It should hold positions where the ball will collide with bounds
    private ArrayList<TrackingDots> trackingDots;
    int accuracy = 100;

    Tracker(int GAME_WIDTH, int GAME_HEIGHT, int BALL_DIAMETER, int PADDLE_WIDTH){
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.GAME_WIDTH = GAME_WIDTH;
        this.BALL_DIAMETER = BALL_DIAMETER;
        this.PADDLE_WIDTH = PADDLE_WIDTH;
        futurePos = new ArrayList<>();
        trackingDots = new ArrayList<>();
    }

    public void track(int ballX, int ballY, int changeInX, int changeInY){
        resetFuturePos();
        if (changeInX == 0 || changeInY == 0){
            System.out.println("ERROR: Kindly hold");
            return;
        }
        populateFuturePos(ballX, ballY, changeInX, changeInY);
    }

    public void track(int ballX, int ballY, int changeInX, int changeInY, int targetX){
        resetFuturePos();
        if (changeInX == 0 || changeInY == 0){
            System.out.println("ERROR: Kindly hold");
            return;
        }
        populateFuturePos(ballX, ballY, changeInX, changeInY, targetX);
    }

    private void populateFuturePos(int ballX, int ballY, int changeInX, int changeInY, int targetX) {
        float m =  changeInX / changeInY;
        Point interceptYPoint, interceptXPoint;
        if (changeInX > 0 ) {
            interceptYPoint = getYIntercept(ballX, ballY, m, GAME_WIDTH - BALL_DIAMETER - PADDLE_WIDTH);
        }
        else {
            interceptYPoint = getYIntercept(ballX, ballY, m, PADDLE_WIDTH);
        }

        if ( changeInY > 0 )
            interceptXPoint = getXIntercept(ballX, ballY, m, GAME_HEIGHT - BALL_DIAMETER);
        else
            interceptXPoint = getXIntercept(ballX, ballY, m);

        if( interceptYPoint.getY() > 0 && interceptYPoint.getY() < GAME_HEIGHT - BALL_DIAMETER){
            futurePos.add(interceptYPoint);
            changeInX *= -1;
            if (targetX == interceptYPoint.getX()) {
                System.out.format("The final Point is : X : %d Y: %d \n", (int)interceptYPoint.getX(), (int)interceptYPoint.getY());
                return;
            }else{
                System.out.format("The next Point is : X : %d Y: %d \n", (int)interceptYPoint.getX(), (int)interceptYPoint.getY());
            }
        }
        futurePos.add(interceptXPoint);
        populateFuturePos(  (int)interceptXPoint.getX(),
                (int)interceptXPoint.getY(),
                changeInX,
                changeInY * -1,
                targetX);
    }


    public void draw(Graphics g) {
        if (this.futurePos.isEmpty()) {
            return;
        }
        for(var pt : this.futurePos){
            trackingDots.add(new TrackingDots(pt));
        }
        for(var trackingDot : trackingDots) {
            trackingDot.draw(g);
        }
    }

    private void populateFuturePos(int ballX, int ballY, int changeInX, int changeInY) {
        float m =  changeInX / changeInY;
        Point interceptYPoint, interceptXPoint;
        if (changeInX > 0 ) {
            interceptYPoint = getYIntercept(ballX, ballY, m, GAME_WIDTH - BALL_DIAMETER - PADDLE_WIDTH);
        }
        else {
            interceptYPoint = getYIntercept(ballX, ballY, m, PADDLE_WIDTH);
        }

        if ( changeInY > 0 )
            interceptXPoint = getXIntercept(ballX, ballY, m, GAME_HEIGHT - BALL_DIAMETER);
        else
            interceptXPoint = getXIntercept(ballX, ballY, m);

        if( interceptYPoint.getY() > 0 && interceptYPoint.getY() < GAME_HEIGHT - BALL_DIAMETER){
            futurePos.add(interceptYPoint);
            return;
        }
        futurePos.add(interceptXPoint);
        populateFuturePos(  (int)interceptXPoint.getX(),
                            (int)interceptXPoint.getY(),
                            changeInX,
                            changeInY * -1);
    }

    private Point getXIntercept(int ballX, int ballY, float m){
        return new Point((int)((m * ballX - ballY) / m),0);
    }
    private Point getXIntercept(int ballX, int ballY, float m, int yInterceptPoint){
        return new Point((int)((m * ballX + yInterceptPoint - ballY) / m), yInterceptPoint);
    }

    private Point getYIntercept(int ballX, int ballY, float m, int xInterceptPoint){
        return new Point(xInterceptPoint ,(int)(m * ( xInterceptPoint - ballX )  + ballY));
    }

    private void resetFuturePos() {
        futurePos.clear();
        trackingDots.clear();
    }

    public int getY(){
        if (futurePos.isEmpty()){
            return 0;
        }
        return (int) futurePos.get(futurePos.size() - 1).getY();
    }

    public int getY(int targetX){
        if (futurePos.isEmpty()){
            return 0;
        }
        for(var pt : futurePos){
            if (pt.getX() == targetX){
                return (int) pt.getY();
            }
        }
        return getY();
    }

    public int getX(){
        if (futurePos.isEmpty()){
            return 0;
        }
        return (int) futurePos.get(futurePos.size() - 1).getX();
    }



    public boolean isTracking(){
        return !futurePos.isEmpty();
    }
}