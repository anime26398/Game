package com.example.arihantjain.game2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Arihant Jain on 12/18/2016.
 */

public class Player extends GameObject{
    private Bitmap spriteSheet;
    private int score;
    private boolean up;
    private boolean down;
    private boolean fire;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private int bestScore;
    public Player(Bitmap res ,int w,int h,int numFrames){
        x = 100;
        y = GameView.HEIGHT/2 -50;
        dy = 0;
        score = 0;
        height = h;
        width =w;

        Bitmap[] image = new Bitmap[numFrames];
        spriteSheet = res;
        for(int i=0; i<image.length; i++){
            image[i] = Bitmap.createBitmap(spriteSheet,0,i*height,width,height);
        }
        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();


    }

    public void setUp(boolean b){
        up = b;
    }
    public void setDown(boolean b){
        down = b;
    }
    public void setFire(boolean b){fire = b;}
    public void update(){
        animation.update();
        if(up){
            y-=10;
            if(y<=0 )
                y = 0;
        }
        if(down) {
           y+=10;
            if(y>=GameView.HEIGHT-99-100)
                y = GameView.HEIGHT-99-100;
        }
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public int getScore(){return  score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing =b;}
    public void resetScore(){score = 0;}
    public void setScore(int s){
        score = s;
        if(bestScore<score){
            bestScore = score;
        }
    }
    public int getBest(){
        return bestScore;
    }
}

