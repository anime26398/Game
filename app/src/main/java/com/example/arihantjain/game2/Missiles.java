package com.example.arihantjain.game2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Arihant Jain on 12/19/2016.
 */

public class Missiles extends GameObject{
    public Missiles(int x,int y){
        this.x = x;
        this.y = y;
    }
    public void update(){
        x+=10;
    }
    public void draw(Canvas canvas){
        Paint paint =new Paint();
        paint.setColor(Color.parseColor("#5434ff"));
        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(x,y,x+30,y+10,paint);
    }

}
