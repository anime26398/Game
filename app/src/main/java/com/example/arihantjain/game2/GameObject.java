package com.example.arihantjain.game2;

import android.graphics.Rect;

/**
 * Created by Arihant Jain on 12/18/2016.
 */

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Rect getRect() {
        return new Rect(getX(),getY(),x+width,y+height);
    }
}
