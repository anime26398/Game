package com.example.arihantjain.game2;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Arihant Jain on 12/18/2016.
 */

public class GameArea extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }
}
