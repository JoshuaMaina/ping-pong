package com.joshmaina.game;

import java.awt.*;

public class TrackingDots extends Rectangle {
    Point pt;
    boolean hit;
    static final int DIAMETER = 5;
    TrackingDots(Point pt) {
        super((int) pt.getX(), (int) pt.getY(), TrackingDots.DIAMETER, TrackingDots.DIAMETER);
    }

    public void draw(Graphics g){
        g.setColor(hit ? Color.green : Color.red);
        g.fillOval(x, y, width, height);;
    }
}
