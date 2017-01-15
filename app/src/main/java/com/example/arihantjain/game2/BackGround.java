package com.example.arihantjain.game2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Arihant Jain on 12/18/2016.
 */

public class BackGround {
    private Bitmap image;
    int x,y,speed;
    BackGround(Bitmap res){
        this.image = res;
        this.speed = GameView.MOVESPEED;
    }
    public void update(){
        x+=speed;
        if(x<-image.getWidth()){
            x =0;
        }
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
        if(x<0){
            canvas.drawBitmap(image,x+image.getWidth(),y,null);
        }
    }
}
