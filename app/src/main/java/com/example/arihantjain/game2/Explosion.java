package com.example.arihantjain.game2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Arihant Jain on 12/19/2016.
 */

public class Explosion {
    private int x;
    private int y;
    private int height;
    private int width;
    private int row;
    private Animation animation = new Animation();
    private Bitmap spriteSheet;

    public Explosion(Bitmap res,int x,int y,int w,int h,int numFrames){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        row = 0;

        Bitmap[] image = new Bitmap[numFrames];
        spriteSheet = res;
        for(int i =0; i<image.length;i++){
            if(i%5 == 0 && i>0)row++;
                image[i] = Bitmap.createBitmap(spriteSheet,(i-5*row)*width,row*height,width,height);
        }
        animation.setFrames(image);
        animation.setDelay(10);


    }
    public void draw(Canvas canvas){
        if(!animation.playedOnce()){
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }
    }
    public void update(){
        if(!animation.playedOnce()){
            animation.update();
        }
    }
    public int getHeight(){
        return height;
    }
}
