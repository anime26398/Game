package com.example.arihantjain.game2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Arihant Jain on 12/19/2016.
 */

public class Controls {
    protected int drawLocationX;
    protected Bitmap spriteSheet;
    protected int x,y,width,height;

    public Controls(Bitmap image, int height, int width, int x, int y,int d) {
        this.height = height;
        this.width = width;
        this.y = y;
        this.x = x;
        this.drawLocationX = d;
        this.spriteSheet = Bitmap.createBitmap(image,x,y,width,height);
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(spriteSheet,drawLocationX,GameView.HEIGHT-100,null);
    }
    public boolean isWithin(float xpos,float ypos){
        if(xpos>=drawLocationX*GameView.scaleX && xpos<=(drawLocationX + width)*GameView.scaleX){
            System.out.println("insdie 1");
            if(ypos>=(GameView.HEIGHT-100)*GameView.scaleY && ypos<=(GameView.HEIGHT-100 +height)*GameView.scaleY){
                System.out.println("inside 2");
                return true;
            }
        }
        return false;
    }
}
