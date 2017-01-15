package com.example.arihantjain.game2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Arihant Jain on 12/18/2016.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    static float scaleX;
    static float scaleY;
    public  static int WIDTH = 640;
    public static int HEIGHT = 424;
    private Random random = new Random();
    private SurfaceHolder surfaceHolder;
    private MyThread thread;
    private BackGround backGround;
    public static final int MOVESPEED = -5;
    private Player player;
    private ArrayList<Enemy> enemies;
    private long enemyStartTime;
    private ArrayList<Missiles> missiles;
    private long missileStartTime;
    private Controls UP,DOWN;
    private long startReset;
    private boolean reset;
    private Explosion explosion;
    private boolean disappear;
    private long startExplosion;
    private boolean newGame;
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);


        setFocusable(true);
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // BACKGROUND
        Bitmap back = BitmapFactory.decodeResource(getResources(),R.drawable.back);
        backGround = new BackGround(back);
        WIDTH = back.getWidth();
        HEIGHT = back.getHeight();
        //PLAYER
        Bitmap playerBit = BitmapFactory.decodeResource(getResources(),R.drawable.rocketon);
        player = new Player(playerBit,playerBit.getWidth(),playerBit.getHeight()/4,4);

        //CONTROLS
        Bitmap control = BitmapFactory.decodeResource(getResources(),R.drawable.controls);
        UP = new Controls(control,control.getHeight()/2,control.getWidth()/2,0,0,600);
        DOWN = new Controls(control,control.getHeight()/2,control.getWidth()/2,0,control.getHeight()/2,700);
        thread = new MyThread(getHolder(),this);


        missiles = new ArrayList<Missiles>();
        enemies = new ArrayList<Enemy>();
        newGame = true;
        //blast


        missileStartTime = System.nanoTime();

        enemyStartTime = System.nanoTime();

        thread = new MyThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        int counter = 0;
        while (retry && counter <1000){
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void update(){
        if(player.getPlaying()){
        backGround.update();
        player.update();

            //adding enemies
            long enemiesElapsed = (System.nanoTime()-enemyStartTime)/1000000;
            if(enemiesElapsed>(2000 - player.getScore()/4)) {
                int [] allEnemies = {R.drawable.alien1,R.drawable.alien2};
                 int currentEnemy = allEnemies[Math.abs(random.nextInt())%2];
                Bitmap enemyImage = BitmapFactory.decodeResource(getResources(), currentEnemy);
                // first always goes to middle

                    enemies.add(new Enemy(enemyImage, WIDTH + 10, HEIGHT / 2, 155, 96, player.getScore()));

                    enemies.add(new Enemy(enemyImage, WIDTH + 10, (int) (random.nextDouble() * (HEIGHT-199)), 155, 96, player.getScore()));

                // reset timer
                enemyStartTime = System.nanoTime();
            }

                for(int i=0;i<enemies.size();i++){
                    enemies.get(i).update();
                    if(collision(enemies.get(i),player)){
                        enemies.remove(i);
                        disappear = true;
                        reset = true;
                        newGame = false;
                        startExplosion = System.nanoTime();
                        player.setPlaying(false);
                        break;
                    }
                    if(enemies.get(i).getX()<-100){
                        enemies.remove(i);
                        break;
                    }
                }

            long elapsed = (System.nanoTime()-missileStartTime)/1000000;
            if(elapsed>500 ){
                missiles.add(new Missiles(player.getX()+100,player.getY()+50));
                missileStartTime =System.nanoTime();
            }
            for(int i=0;i<missiles.size();i++){
                missiles.get(i).update();
                for(int j=0;j<enemies.size();j++) {
                     if(collision(missiles.get(i),enemies.get(j))){
                         player.setScore(player.getScore()+1);
                         enemies.remove(j);
                         missiles.remove(i);
                         break;
                     }
                }
                if(missiles.get(i).getX()>WIDTH-100){
                    missiles.remove(i);
                }
            }
        }
        if(disappear){
            if(reset){
                Bitmap exp = BitmapFactory.decodeResource(getResources(),R.drawable.explosion);
                explosion = new Explosion(exp,player.getX(),player.getY()-20,100,100,25);
                reset = false;
            }
            long elapsedexp = (System.nanoTime() - startExplosion)/1000000;
                explosion.update();
            System.out.println("explosion is updating");
            if(elapsedexp>1000){
                newGame = true;
                disappear = false;
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(!player.getPlaying() ){
                if(newGame){
                player.setPlaying(true);
                    resetGame();
                }
            }
            else {
                if(UP.isWithin(event.getX(),event.getY())){
                    player.setUp(true);
                }

                if(DOWN.isWithin(event.getX(),event.getY())){
                    player.setDown(true);
                }
            }
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            player.setUp(false);
            player.setDown(false);

            return true;
        }
        return super.onTouchEvent(event);
    }

    public boolean collision(GameObject a,GameObject b){

        if(Rect.intersects(a.getRect(),b.getRect())){
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        scaleX = getWidth()/(WIDTH*1.f);
        scaleY = getHeight()/(HEIGHT*1.f);
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleX,scaleY);
            backGround.draw(canvas);
            if(!disappear) {
                player.draw(canvas);
            }
            UP.draw(canvas);
            DOWN.draw(canvas);
            for(Missiles msi:missiles){
                msi.draw(canvas);
            }
            for(Enemy emy:enemies){
                emy.draw(canvas);
            }
            if(disappear){
                if(!reset)
                explosion.draw(canvas);
                System.out.print("drawing exposion");
            }
            drawText(canvas);
            canvas.restoreToCount(savedState);
        }
    }
    public void resetGame(){
            missiles.clear();
            enemies.clear();
        player.setY(HEIGHT/2 - 50);
        player.resetScore();
    }
    public void drawText(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        canvas.drawText("SCORE: " + (player.getScore()),10,HEIGHT-10,paint);
        canvas.drawText("BEST: " + player.getBest(),WIDTH - 615,HEIGHT-10,paint);

        if(!player.getPlaying() && newGame){
            Paint paint1 = new Paint();
            paint1.setColor(Color.WHITE);
            paint1.setTextSize(40);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            canvas.drawText("PRESS TO START",WIDTH/2 -50,HEIGHT/2,paint1);
            canvas.drawText("A NEWGAME",WIDTH/2 -50,HEIGHT/2+40,paint1);
        }
    }
}
