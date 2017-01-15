package com.example.arihantjain.game2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    Button startBtn,exitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeButtons();

    }

    // Initializing buttons

    public void initializeButtons(){

        startBtn = (Button)findViewById(R.id.startBtn);
        exitBtn = (Button)findViewById(R.id.exitBtn);

        startBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent gameIntent = new Intent(MainActivity.this,GameArea.class);
                        startActivityForResult(gameIntent,1234);
                    }
                }
        );
        exitBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );
    }
}
