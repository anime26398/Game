package com.example.arihantjain.game2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Arihant Jain on 12/19/2016.
 */

public class Enemy extends GameObject{
    private int score;
    private int speed;
    private Bitmap bitmap;
    private Random rand = new Random();
    public Enemy(Bitmap res,int x ,int y,int w,int h,int s){
        super.x = x;
        super.y = y;
        height = h;
        width = w;
        score = s;
        speed = 7 + (int)(rand.nextDouble()*score/30);
        if(speed>40){
            speed = 40;
        }
        bitmap = Bitmap.createBitmap(res,0,0,width,height);
    }
    public void update(){
            x-=speed;
    }
    public void draw(Canvas canvas){
        try{
            canvas.drawBitmap(bitmap,x,y,null );
        }
        catch (Exception e){System.out.println("Error in draw");}
    }

    @Override
    public int getWidth(){
        return width-10;
    }


}
